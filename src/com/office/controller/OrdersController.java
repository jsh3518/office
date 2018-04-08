package com.office.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.Customer;
import com.office.entity.Offer;
import com.office.entity.Orders;
import com.office.entity.Page;
import com.office.entity.PubCode;
import com.office.entity.Subscription;
import com.office.service.CustomerService;
import com.office.service.OfferService;
import com.office.service.OrdersService;
import com.office.service.PubCodeService;
import com.office.util.Const;
import com.office.util.FileUploadUtil;
import com.office.util.RestfulUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/orders")
public class OrdersController {
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private OfferService offerService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PubCodeService pubCodeService;
	
	private static Logger logger = Logger.getLogger(OrdersController.class);//输出Log日志
	
	@RequestMapping(value="/listOrders")
	public String listOrders(Model model,Page page,HttpSession session,HttpServletRequest req){
		Map<String,Object> map = new HashMap<String,Object>();
		
		String companyName = req.getParameter("companyName")==null?"":req.getParameter("companyName");
		String domain = req.getParameter("domain")==null?"":req.getParameter("domain");
		String ordersNo = req.getParameter("ordersNo")==null?"":req.getParameter("ordersNo");
		String flag = req.getParameter("flag")==null?"":req.getParameter("flag");
		//如果为订单确认则只查询本代理商创建的订单；否则查询所有订单
		if(!"audit".equals(flag)){
			String userId = (String) session.getAttribute(Const.SESSION_USER_ID);
			map.put("createUser", userId);
		}
		map.put("page", page);
		map.put("companyName", companyName);
		map.put("domain", domain);
		map.put("ordersNo", ordersNo);
		List<Orders> ordersList = ordersService.listOrders(map);
		HashMap<String,String> statusMap = pubCodeService.codeMapByClass("DDZT");
		model.addAttribute("ordersList", ordersList);
		model.addAttribute("page", page);
		model.addAttribute("companyName", companyName);
		model.addAttribute("domain", domain);
		model.addAttribute("ordersNo", ordersNo);
		model.addAttribute("statusMap", statusMap);
		model.addAttribute("flag", flag);
		return "orders/orders_list";
	}
	/**
	 * 添加客户初始化页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/forNew")
	public String forNew(Model model,Customer customer,HttpSession session){

		List<PubCode> billingList = pubCodeService.listPubCodeByClass("BILLINGCYCLE");
		List<Offer> listParentOffer = offerService.listOfferByLevel(1);
		List<Offer> offerList = new ArrayList<Offer>();
		for(Offer offer:listParentOffer){
			String id = offer.getOfferId();
			offer.setSubOffer(offerService.listOfferByParent(id));
			offerList.add(offer);
		}
		model.addAttribute("billingList", billingList);
		model.addAttribute("offerList", offerList);
		session.setAttribute("tempCustomer", customer);
		return "orders/orders_add";
	}

	/**
	 * 保存订单信息（如果用户不存在，则先保存用户信息）
	 * @param orders
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addOrders",method=RequestMethod.POST)
	public ModelAndView addOrders(Orders orders,HttpSession session,HttpServletRequest req){
		String userId = (String) session.getAttribute(Const.SESSION_USER_ID);
		Date day=new Date();
		Customer customer = (Customer)session.getAttribute("tempCustomer");
		if(customer!=null&&customer.getId()==null){
			customer.setCountry("CN");
			customer.setCreateTime(day);
			customer.setCreateUser(userId);
			//插入之后customer主键值被更新
			customerService.insertCustomer(customer);
		}
		
		String maxNo = ordersService.getMaxOrdersNo(day);
		String ordersNo;
		if(maxNo==null||"".equals(maxNo)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			ordersNo = "ORDER-VSTECS-"+ sdf.format(day)+"00001";
		}else{
			Long num = Long.valueOf(maxNo.substring(maxNo.length()-13))+1;
			ordersNo = "ORDER-VSTECS-"+num;
		}

		orders.setOrdersNo(ordersNo);
		orders.setCustomerId(customer.getId());
		orders.setStatus("0");//0:新增;1:已提交;2:已审核。
		orders.setCreateTime(day);
		orders.setCreateUser(userId);
		ordersService.insertOrders(orders);
		String[] checkbox = req.getParameterValues("checkbox");
		String quantity;
		List<Subscription> subscriptionList = new ArrayList<Subscription>();

		for(String str:checkbox){
			quantity = req.getParameter("quantity_"+str);
			Subscription subscription = new Subscription();
			subscription.setQuantity(quantity);
			subscription.setOfferId(str);
			subscription.setOrdersId(orders.getId());
			subscription.setBillingCycle(orders.getBillingCycle());
			subscription.setCreateUser(userId);
			subscriptionList.add(subscription);
		}
		ordersService.insertSubscription(subscriptionList);
		
		//跳转至付款信息界面
		ModelAndView mv = new ModelAndView();
		String ordersId = Integer.toString(orders.getId());
		orders.setCustomer(customer);
		Map<String, List<Subscription>> subscriptionMap = getSubscriptionMap(ordersId);
		List<PubCode> paymentList = pubCodeService.listPubCodeByClass("PAYMENT");
		HashMap<String,String> statusMap = pubCodeService.codeMapByClass("DDZT");
		HashMap<String,String> billingCycleMap = pubCodeService.codeMapByClass("BILLINGCYCLE");
		mv.addObject("orders", orders);
		mv.addObject("subscriptionMap", subscriptionMap);
		mv.addObject("paymentList", paymentList);
		mv.addObject("statusMap", statusMap);
		mv.addObject("billingCycleMap", billingCycleMap);
		//返回订单信息
		mv.setViewName("orders/orders_detail");
		return mv;
	}
	
	/**
	 * 根据订单id获取订单基本信息
	 * @param model
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getOrders")
	public String getOrdersById(Model model,HttpServletRequest req){
		
		String id = req.getParameter("id")==null?"":req.getParameter("id");
		String flag = req.getParameter("flag")==null?"":req.getParameter("flag");
		Orders orders = ordersService.getOrdersById(id);
		Map<String, List<Subscription>> subscriptionMap = getSubscriptionMap(id);
		List<PubCode> paymentList = pubCodeService.listPubCodeByClass("PAYMENT");
		HashMap<String,String> statusMap = pubCodeService.codeMapByClass("DDZT");
		HashMap<String,String> billingCycleMap = pubCodeService.codeMapByClass("BILLINGCYCLE");
		model.addAttribute("orders", orders);
		model.addAttribute("subscriptionMap", subscriptionMap);
		model.addAttribute("paymentList", paymentList);
		model.addAttribute("statusMap", statusMap);
		model.addAttribute("billingCycleMap", billingCycleMap);
		if("audit".equals(flag)){
			return "orders/orders_audit";
		}else{
			return "orders/orders_detail";
		}
	}
	
	/**
	 * 代理商确认订单信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/confirmOrders",method=RequestMethod.POST)
	public String confirmOrders(MultipartHttpServletRequest request,Model model) {
		
		String payment = request.getParameter("payment")==null?"":request.getParameter("payment");
		String ordersNo = request.getParameter("ordersNo")==null?"":request.getParameter("ordersNo");
		Integer id = request.getParameter("id") ==null?null:Integer.valueOf(request.getParameter("id"));

		Orders orders = new Orders();
		orders.setId(id);
		orders.setOrdersNo(ordersNo);
		orders.setPayment(payment);
		orders.setStatus("1");
		//现付需要上传付款凭证
		if("0".equals(payment)){
			MultipartFile mfile = (MultipartFile)request.getFile("voucher");
			String filePath = request.getSession().getServletContext().getRealPath("/") + "files"+File.separator;
			logger.debug("附件上传目录："+filePath);
			String fileName = ordersNo +"@_@" + mfile.getOriginalFilename();
			orders.setFile(fileName);//附件名称
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
			FileUploadUtil.uploadFile(mfile,filePath+fileName);
		}

		ordersService.updateOrders(orders);
        return "redirect:listOrders.html";
	}
	
	/**
	 * 审核订单信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/auditOrders")
	public String auditOrders(HttpServletRequest request,Model model) {

		String id = request.getParameter("id") ==null?"":request.getParameter("id");
		String opinion = request.getParameter("opinion") ==null?"":request.getParameter("opinion");
		Orders tmpOrders = new Orders();
		if(!"".equals(id)){
			tmpOrders.setId(Integer.valueOf(id));
		}
		//1:审核通过;0:审核不通过
		if("1".equals(opinion)){
			tmpOrders.setStatus("2");
			//审核通过，将订阅数据发送到Office
			
			HttpSession session = request.getSession();
			String access_token = (String)session.getAttribute(Const.ACCESS_TOKEN);
			if(access_token == null){//如果session中不包含access_token，则通过调用接口重新获取token
				JSONObject resultJson = RestfulUtil.getToken();
				access_token = resultJson.get("access_token")==null?"":resultJson.get("access_token").toString();
				session.setAttribute(Const.ACCESS_TOKEN, access_token);
			}
			Orders orders = ordersService.getOrdersById(id);
			Customer customer = orders.getCustomer();
			String tenantId = customer.getTenantId();
			//如果tenantId属性为空，说明该客户信息未同步至微软O365，需要在O365新增客户信息
			if(tenantId==null||"".equals(tenantId)){
				JSONObject companyJson = new JSONObject();
				String attr = " {'objectType': 'CustomerCompanyProfile'}";
				companyJson.put("tenantId", null);//"5978683a-9b93-470e-b651-2328e863ec6c"
				companyJson.put("domain", customer.getDomain()+".partner.onmschina.cn");
				companyJson.put("companyName", customer.getCompanyName());
				companyJson.put("attributes", JSONObject.fromObject(attr));
				JSONObject addressJson = new JSONObject();
				addressJson.put("country", "CN");
				addressJson.put("state", customer.getAbbr());
				addressJson.put("city", customer.getCityName());
				addressJson.put("region", customer.getRegionName());
				addressJson.put("addressLine1", customer.getAddress());
				addressJson.put("postalCode", customer.getPostalCode());
				addressJson.put("firstName", customer.getFirstName());
				addressJson.put("lastName", customer.getLastName());
				addressJson.put("phoneNumber", customer.getPhoneNumber());
				JSONObject billingJson = new JSONObject();
				attr = "{'objectType': 'CustomerBillingProfile'}";
				billingJson.put("attributes", JSONObject.fromObject(attr));
				billingJson.put("defaultAddress", addressJson);
				billingJson.put("id", null);
				billingJson.put("firstName", customer.getFirstName());
				billingJson.put("lastName", customer.getLastName());
				billingJson.put("email", customer.getEmail());
				billingJson.put("culture", "zh-cn");
				billingJson.put("language", "zh-CHS");
				billingJson.put("companyName", customer.getCompanyName());
				JSONObject customerJson = new JSONObject();
				customerJson.put("companyProfile", companyJson);
				customerJson.put("billingProfile", billingJson);
				String targetURL = "https://partner.partnercenterapi.microsoftonline.cn/v1/customers"; 
				String method = "POST";
				Map<String, String> paramHeader = new HashMap<String, String>();
				paramHeader.put("Accept", "application/json");
				paramHeader.put("Content-Type", "application/json");
				paramHeader.put("Authorization",  "Bearer "+access_token);
				String paramBody = customerJson.toString();       
				JSONObject resultJson = RestfulUtil.getRestfulData(targetURL,method,paramHeader,paramBody);

				if(resultJson.get("responseCode").toString().startsWith("2")){
					//2018-04-02 该接口返回值前有一个特殊符号，需要截取掉再转换成json
					String result = resultJson.get("result").toString();
					if(result.indexOf("{")>=0){
						JSONObject reCustomerJson = JSONObject.fromObject(result.substring(result.indexOf("{")));
						tenantId = reCustomerJson.get("id")==null?"":reCustomerJson.get("id").toString();
						customerService.updateTenantId(customer.getId(), tenantId);
					}
				}
			}
			
			List<Subscription> subscriptionList = ordersService.getSubscription(id);
			JSONObject orderJson = new JSONObject();
			orderJson.put("referenceCustomerId", tenantId);
			orderJson.put("billingCycle", orders.getBillingCycle());
			orderJson.put("creationDate", null);
			orderJson.put("attributes", "'objectType': 'order'");
			JSONArray jsonArr = new JSONArray();  
			for(int i=0;i<subscriptionList.size();i++){
				Subscription subscription = subscriptionList.get(i);
				JSONObject subscriptionJson = new JSONObject();
				subscriptionJson.put("lineItemNumber", i);
				subscriptionJson.put("offerId", subscription.getOfferId());
				subscriptionJson.put("friendlyName", subscription.getOfferName());
				subscriptionJson.put("quantity", subscription.getQuantity());
				//此处需要验证MPNID是否有效
				if(orders.getMpnId()!=null){
					JSONObject json = getMpnId(access_token,orders.getMpnId());
					if(json.get("responseCode").toString().startsWith("2")){
						subscriptionJson.put("partnerIdOnRecord",orders.getMpnId());
					}
				}
				subscriptionJson.put("attributes", "'objectType': 'orderLineItems'");
				jsonArr.add(subscriptionJson);
			}
			orderJson.put("lineItems", jsonArr);
			String targetURL = "https://partner.partnercenterapi.microsoftonline.cn/v1/customers/"+tenantId+"/orders";
			String method = "POST";
			Map<String, String> paramHeader = new HashMap<String, String>();
			paramHeader.put("Accept", "application/json");
			paramHeader.put("Content-Type", "application/json");
			paramHeader.put("Authorization",  "Bearer "+access_token);
			String paramBody = orderJson.toString();       
			JSONObject resultJson = RestfulUtil.getRestfulData(targetURL,method,paramHeader,paramBody);
			if(resultJson.get("responseCode").toString().startsWith("2")){
				//2018-04-02 该接口返回值前有一个特殊符号，需要截取掉再转换成json
				String result = resultJson.get("result").toString();
				JSONArray orderArr = new JSONArray();
				if(result.indexOf("{")>=0){
					JSONObject reOrderJson = JSONObject.fromObject(result.substring(result.indexOf("{")));
					String orderId = reOrderJson.get("id")==null?"":reOrderJson.get("id").toString();
					//ordersService.updateOrderId(Integer.valueOf(id), orderId);
					tmpOrders.setOrderId(orderId);//对应O365订单Id
					orderArr = JSONArray.fromObject(reOrderJson.get("lineItems"));
				}

				for (Object obj : orderArr){
					JSONObject o = (JSONObject) obj;
					Subscription newSubscription = new Subscription();
					newSubscription.setSubscriptionId(o.getString("subscriptionId"));
					newSubscription.setOfferId(o.getString("offerId"));
					newSubscription.setOrdersId(Integer.valueOf(id));
					ordersService.updateSubscription(newSubscription);
				}
			}
		}else if("0".equals(opinion)){
			tmpOrders.setStatus("3");
		}
		
		ordersService.updateOrders(tmpOrders);
        return "redirect:listOrders.html?flag=audit";
	}

	/**
	 * 根据订单Id查询订阅信息，并对订阅数据进行重新组装。
	 * 组装格式HashMap(key:parentName,value:List<Subscription>)
	 * @param id
	 * @return
	 */
	private Map<String, List<Subscription>> getSubscriptionMap( String id){
		List<Subscription> subscriptionList = ordersService.getSubscription(id);
		Map<String, List<Subscription>> subscriptionMap = new HashMap<>();
		for (Subscription subscription : subscriptionList) {
			List<Subscription> tempList = subscriptionMap.get(subscription.getParentName());
			//如果取不到数据,那么直接new一个空的ArrayList
			if (tempList == null) {
				tempList = new ArrayList<>();
				tempList.add(subscription);
				subscriptionMap.put(subscription.getParentName(), tempList);
			}else {
				//某个subscriptionMap之前已经存放过了,则直接追加数据到原来的List里
				tempList.add(subscription);
			}
		}
		return subscriptionMap;
	}
	
	
	/**
	 * 判断域名是否可用
	 * @param code
	 * @param response
	 */
	@RequestMapping(value="/checkMpnId")
	public void checkMpnId(String mpnId,HttpServletResponse response,HttpSession session){
		
		String access_token = (String)session.getAttribute(Const.ACCESS_TOKEN);
		if(access_token == null){//如果session中不包含access_token，则通过调用接口重新获取token
			JSONObject resultJson = RestfulUtil.getToken();
			access_token = resultJson.get("access_token")==null?"":resultJson.get("access_token").toString();
			session.setAttribute(Const.ACCESS_TOKEN, access_token);		
		}
		JSONObject object = getMpnId(access_token,mpnId);
		
		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			out.write(object.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public JSONObject getMpnId(String access_token,String mpnId){
		String targetURL = "https://partner.partnercenterapi.microsoftonline.cn/v1/profiles/mpn?mpnId="+mpnId; 
		String method = "GET";
		Map<String, String> paramHeader = new HashMap<String, String>();
		paramHeader.put("Accept", "application/json");
		paramHeader.put("Authorization",  "Bearer "+access_token);
		return  RestfulUtil.getRestfulData(targetURL,method,paramHeader,null);
	}
}
