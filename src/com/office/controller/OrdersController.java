package com.office.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.Credit;
import com.office.entity.Customer;
import com.office.entity.Offer;
import com.office.entity.OfferPrice;
import com.office.entity.Orders;
import com.office.entity.Page;
import com.office.entity.PubCode;
import com.office.entity.Relationship;
import com.office.entity.Subscription;
import com.office.entity.OrdersDetail;
import com.office.entity.Organ;
import com.office.entity.User;
import com.office.service.CreditService;
import com.office.service.CustomerService;
import com.office.service.OfferPriceService;
import com.office.service.OfferService;
import com.office.service.OrdersService;
import com.office.service.OrganService;
import com.office.service.PubCodeService;
import com.office.util.Const;
import com.office.util.FileUploadUtil;
import com.office.util.RestfulUtil;
import com.office.util.Tools;

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
	@Autowired
	private OfferPriceService offerPriceService;
	@Autowired
	private CreditService creditService;
	@Autowired
	private OrganService organService;
	
	private static Logger logger = Logger.getLogger(OrdersController.class);//输出Log日志
	
	@RequestMapping(value="/listOrders")
	public String listOrders(Model model,Page page,HttpSession session,HttpServletRequest req){
		Map<String,Object> map = new HashMap<String,Object>();
		
		String companyName = req.getParameter("companyName")==null?"":req.getParameter("companyName");
		String domain = req.getParameter("domain")==null?"":req.getParameter("domain");
		String ordersNo = req.getParameter("ordersNo")==null?"":req.getParameter("ordersNo");
		String flag = req.getParameter("flag")==null?"":req.getParameter("flag");
		User user = (User)session.getAttribute(Const.SESSION_USER);
		//如果为总代审核订单，则需查询状态为已提价的订单
		if("audit".equals(flag)){
			map.put("status", "1");
		}else{//否则查询自己创建的订单
			map.put("createUser", user==null?"":user.getLoginname());
			map.put("status", flag);
		}
		map.put("page", page);
		map.put("companyName", companyName);
		map.put("domain", domain);
		map.put("ordersNo", ordersNo);
		List<Orders> ordersList = ordersService.listOrders(map);
		HashMap<String,String> statusMap = pubCodeService.codeMapByClass("ORDERSTATUS");
		model.addAttribute("ordersList", ordersList);
		model.addAttribute("page", page);
		model.addAttribute("companyName", companyName);
		model.addAttribute("domain", domain);
		model.addAttribute("ordersNo", ordersNo);
		model.addAttribute("statusMap", statusMap);
		model.addAttribute("flag", flag);
		model.addAttribute("roleId", user==null?"":user.getRoleId());
		return "orders/orders_list";
	}
	/**
	 * 添加客户初始化页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/forNew")
	public String forNew(Model model,Customer customer,HttpSession session){

		User user= (User)session.getAttribute(Const.SESSION_USER);
		customer.setCountry("CN");
		customer.setCreateTime(new Date());
		customer.setCreateUser(user==null?null:user.getLoginname());
		customer.setStatus("0");//客户新增
		customerService.insertCustomer(customer);
		List<PubCode> billingList = pubCodeService.listPubCodeByClass("BILLINGCYCLE");
		String isTrial = null;
		
		if(user!=null&&user.getRoleId()!=2){
			isTrial = "0";
		}
		List<Offer> listParentOffer = offerService.listOfferByLevel(1,isTrial);
		List<Offer> offerList = new ArrayList<Offer>();
		for(Offer offer:listParentOffer){
			String id = offer.getOfferId();
			offer.setSubOffer(offerService.listOfferByParent(id));
			offerList.add(offer);
		}
		model.addAttribute("billingList", billingList);
		model.addAttribute("offerList", offerList);
		model.addAttribute("customer", customer);
		model.addAttribute("roleId", user==null?null:user.getRoleId());
		//session.setAttribute("tempCustomer", customer);
		return "orders/orders_add";
	}

	/**
	 * 保存订单信息
	 * @param orders
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addOrders",method=RequestMethod.POST)
	public ModelAndView addOrders(Orders orders,HttpSession session,HttpServletRequest req){
		
		String userId = (String) session.getAttribute(Const.SESSION_USER_ID);
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Customer customer = customerService.selectCustomerById(orders.getCustomerId()==null?"":orders.getCustomerId().toString());
		if(orders.getId()==null||"".equals(orders.getId())){
			
			Date day=new Date();
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
			ordersService.insertOrders(orders);//mybaits数据新增后主键自动更新
		}else{//如果订单id存在，说明是订单更新操作，需要将订单明细表数据删除后重新添加
			orders = ordersService.getOrdersById(orders.getId().toString());
			ordersService.deleteOrdersDetail(orders.getId().toString());
		}
		String[] checkbox = req.getParameterValues("checkbox");
		String quantity;
		List<OrdersDetail> ordersDetailList = new ArrayList<OrdersDetail>();
		BigDecimal sum = new BigDecimal(0);
		BigDecimal actualSum = new BigDecimal(0);
		BigDecimal discount = null;
		if(user!=null&&user.getRoleId()==3){//如果是总代理商角色，则折扣可以在订单管理界面进行编辑；如果是代理商角色，则查询折扣自动计算。
			Credit credit = creditService.queryCredit(user.getUserId());
			discount = credit ==null?null:credit.getDiscount();
		}
		for(String str:checkbox){
			quantity = req.getParameter("quantity_"+str);
			OrdersDetail ordersDetail = new OrdersDetail();
			ordersDetail.setQuantity(quantity);
			ordersDetail.setOfferId(str);
			ordersDetail.setOrdersId(orders.getId());
			ordersDetail.setBillingCycle(orders.getBillingCycle());
			ordersDetail.setCreateUser(userId);
			OfferPrice offerPrice = offerPriceService.getPriceByOfferId(str);
			BigDecimal amount = offerPrice.getPriceYear()==null?new BigDecimal(0):offerPrice.getPriceYear().multiply(new BigDecimal(quantity));
			BigDecimal actualAmount = amount.multiply(discount==null?new BigDecimal(1):discount.divide(new BigDecimal(100)));
			ordersDetail.setAmount(amount.setScale(2));
			ordersDetail.setActualAmount(actualAmount.setScale(2));
			ordersDetailList.add(ordersDetail);
			sum = sum.add(amount);
			actualSum = actualSum.add(actualAmount);
		}
		orders.setActualSum(actualSum.setScale(2));
		orders.setSum(sum.setScale(2));
		orders.setDiscount(discount);
		ordersService.updateOrders(orders);
		ordersService.insertOrdersDetailList(ordersDetailList);
		
		//跳转至付款信息界面
		ModelAndView mv = new ModelAndView();
		orders.setCustomer(customer);
		Map<String, List<OrdersDetail>> ordersDetailMap = getOrdersDetailMap(orders.getId().toString());
		List<PubCode> paymentList = pubCodeService.listPubCodeByClass("PAYMENT");
		HashMap<String,String> statusMap = pubCodeService.codeMapByClass("ORDERSTATUS");
		HashMap<String,String> typeMap = pubCodeService.codeMapByClass("ORDERTYPE");
		HashMap<String,String> billingCycleMap = pubCodeService.codeMapByClass("BILLINGCYCLE");
		mv.addObject("orders", orders);
		mv.addObject("ordersDetailMap", ordersDetailMap);
		mv.addObject("paymentList", paymentList);
		mv.addObject("statusMap", statusMap);
		mv.addObject("typeMap", typeMap);
		mv.addObject("billingCycleMap", billingCycleMap);
		mv.addObject("roleId", user.getRoleId());
		mv.addObject("flag", 0);//新增订单
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
	public String getOrdersById(Model model,HttpServletRequest req,HttpSession session){
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String id = req.getParameter("id")==null?"":req.getParameter("id");
		String flag = req.getParameter("flag")==null?"":req.getParameter("flag");
		Orders orders = ordersService.getOrdersById(id);
		Map<String, List<OrdersDetail>> ordersDetailMap = getOrdersDetailMap(id);
		List<PubCode> paymentList = pubCodeService.listPubCodeByClass("PAYMENT");
		List<PubCode> billingCycleList = pubCodeService.listPubCodeByClass("BILLINGCYCLE");
		HashMap<String,String> statusMap = pubCodeService.codeMapByClass("ORDERSTATUS");
		HashMap<String,String> typeMap = pubCodeService.codeMapByClass("ORDERTYPE");
		model.addAttribute("orders", orders);
		model.addAttribute("paymentList", paymentList);
		model.addAttribute("billingCycleList", billingCycleList);
		model.addAttribute("statusMap", statusMap);
		model.addAttribute("typeMap", typeMap);
		model.addAttribute("ordersDetailMap", ordersDetailMap);
		model.addAttribute("roleId", user==null?"":user.getRoleId());
		if("audit".equals(flag)){
			return "orders/orders_audit";
		}else{
			model.addAttribute("flag", flag);//返回查询状态
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
	public String confirmOrders(MultipartHttpServletRequest request,HttpSession session,Model model) {
		
		String payment = request.getParameter("payment")==null?"":request.getParameter("payment");
		String ordersNo = request.getParameter("ordersNo")==null?"":request.getParameter("ordersNo");
		String flag = request.getParameter("flag")==null?"":request.getParameter("flag");
		Integer id = request.getParameter("id") ==null?null:Integer.valueOf(request.getParameter("id"));

		Orders orders = new Orders();
		orders.setId(id);
		orders.setOrdersNo(ordersNo);
		orders.setPayment(payment);
		orders.setStatus("1");
		
		User user = (User)session.getAttribute(Const.SESSION_USER);
		if(user!=null&&user.getRoleId()==2){//如果为总代理商角色，可以手动修改折扣
			BigDecimal sum = request.getParameter("sum")==null||"".equals(request.getParameter("sum"))?null:new BigDecimal(request.getParameter("sum"));
			BigDecimal actualSum = request.getParameter("actualSum")==null||"".equals(request.getParameter("actualSum"))?null:new BigDecimal(request.getParameter("actualSum"));
			BigDecimal discount = request.getParameter("discount")==null||"".equals(request.getParameter("discount"))?null:new BigDecimal(request.getParameter("discount"));
			orders.setSum(sum);
			orders.setActualSum(actualSum);
			orders.setDiscount(discount);
		}
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
        return "redirect:listOrders.html?flag="+flag;
	}

	/**
	 * 审核订单信息
	 * @param request
	 * @param model
	 * @param response
	 */
	@RequestMapping(value="/auditOrders")
	public void auditOrders(HttpServletRequest request,Model model,HttpServletResponse response){
		String message ="success";
		String id = request.getParameter("id") ==null?"":request.getParameter("id");
		String opinion = request.getParameter("opinion") ==null?"":request.getParameter("opinion");
		String billingCycle = request.getParameter("billingCycle") ==null?"monthly":request.getParameter("billingCycle");
		Orders orders = ordersService.getOrdersById(id);
		Orders tmpOrders = new Orders();
		if(orders!=null){
			tmpOrders.setId(orders.getId());
		}
		Customer customer = orders.getCustomer();
		//1:审核通过;0:审核不通过
		if("1".equals(opinion)){
			//审核通过，将订阅数据发送到Office

			String imagePath = request.getSession().getServletContext().getRealPath("/") + "images"+File.separator;
			//如果tenantId属性为空，说明该客户信息未同步至微软O365，需要在O365新增客户信息
			if(customer.getTenantId()==null||"".equals(customer.getTenantId())){		
				customer=customerService.saveCustomer(customer,imagePath);
			}

			if("1".equals(orders.getType())){//如果是坐席续费,更新续订时长（单位：月）
				message = ordersService.renewOrders(orders);
			}else if("2".equals(orders.getType())){//增加坐席
				message = ordersService.increaseOrders(orders);
			}else{//新增订阅
				tmpOrders.setStatus("2");
				tmpOrders.setBillingCycle(billingCycle);
				tmpOrders.setMpnId(orders.getMpnId());
				tmpOrders.setReseller(orders.getReseller());
				tmpOrders.setCustomer(customer);
				
				message = ordersService.CreateOrders(tmpOrders,imagePath);
				if("".equals(message)){
					message = "审核失败！";
				}
			}
		}else if("0".equals(opinion)){//审核不通过，退回。
			tmpOrders.setStatus("3");
			ordersService.updateOrders(tmpOrders);
			if(customer!=null&&"1".equals(customer.getStatus())){//如果客户信息未审核通过，则客户状态变更为“已退回”
				customerService.updateTenantId(customer.getId(), null, null, "3");
			}
		}
		
		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			out.write(message);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据订单Id查询订阅信息，并对订阅数据进行重新组装。
	 * 组装格式HashMap(key:parentName,value:List<OrdersDetail>)
	 * @param id
	 * @return
	 */
	private Map<String, List<OrdersDetail>> getOrdersDetailMap( String id){
		List<OrdersDetail> ordersDetailList = ordersService.getOrdersDetail(id);
		Map<String, List<OrdersDetail>> ordersDetailMap = new HashMap<>();
		for (OrdersDetail ordersDetail : ordersDetailList) {
			List<OrdersDetail> tempList = ordersDetailMap.get(ordersDetail.getParentName());
			//如果取不到数据,那么直接new一个空的ArrayList
			if (tempList == null) {
				tempList = new ArrayList<>();
				tempList.add(ordersDetail);
				ordersDetailMap.put(ordersDetail.getParentName(), tempList);
			}else {
				//某个ordersDetailMap之前已经存放过了,则直接追加数据到原来的List里
				tempList.add(ordersDetail);
			}
		}
		return ordersDetailMap;
	}
	
	/**
	 * 判断域名是否可用
	 * @param code
	 * @param response
	 */
	@RequestMapping(value="/checkMpnId")
	public void checkMpnId(String mpnId,HttpServletResponse response,HttpSession session){
		JSONObject object = RestfulUtil.getMpnId(mpnId);
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

	/**
	 * 
	 * @param request
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/querySubscription")
	public String querySubscription(HttpServletRequest request,Model model,Page page) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		String customerId = request.getParameter("customerId")==null?"":request.getParameter("customerId");
		
		paraMap.put("page", page);
		paraMap.put("customerId", customerId);
		if(user!=null&&user.getRoleId()!=2){//如果不是总代理商，则只查询自己名下的订阅信息
			paraMap.put("createUser", user.getLoginname());
		}
		List<Object> subscriptionList = ordersService.listSubscription(paraMap);
		
		model.addAttribute("subscriptionList", subscriptionList);
		model.addAttribute("page", page);
		model.addAttribute("customerId", customerId);
		return "orders/subscription_list";
	}
	
	/**
	 * 初始化添加订阅
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/forAdd")
	public String forAdd(Model model,String customerId,HttpSession session){

		List<PubCode> billingList = pubCodeService.listPubCodeByClass("BILLINGCYCLE");
		String isTrial = null;
		User user= (User)session.getAttribute(Const.SESSION_USER);
		if(user!=null&&user.getRoleId()!=null&&user.getRoleId()!=2){
			isTrial = "0";
		}
		List<Offer> listParentOffer = offerService.listOfferByLevel(1,isTrial);
		List<Offer> offerList = new ArrayList<Offer>();
		for(Offer offer:listParentOffer){
			String id = offer.getOfferId();
			offer.setSubOffer(offerService.listOfferByParent(id));
			offerList.add(offer);
		}

		model.addAttribute("billingList", billingList);
		model.addAttribute("offerList", offerList);
		model.addAttribute("flag", "new");
		model.addAttribute("roleId", user==null?null:user.getRoleId());
		model.addAttribute("customer", customerService.selectCustomerById(customerId));
		return "orders/orders_add";
	}
	
	
	/**
	 * 修改订单信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/forEdit")
	public String forEdit(Model model,String ordersId,HttpSession session){
		
		Orders orders = ordersService.getOrdersById(ordersId);
		List<OrdersDetail> ordersDetailList = ordersService.getOrdersDetail(ordersId);
		Map<String, String> ordersDetailMap = new HashMap<>();
		for (OrdersDetail ordersDetail : ordersDetailList) {
			ordersDetailMap.put(ordersDetail.getOfferId(), ordersDetail.getQuantity());
		}
		List<PubCode> billingList = pubCodeService.listPubCodeByClass("BILLINGCYCLE");
		String isTrial = null;
		User user= (User)session.getAttribute(Const.SESSION_USER);
		if(user!=null&&user.getRoleId()!=2){
			isTrial = "0";
		}
		List<Offer> listParentOffer = offerService.listOfferByLevel(1,isTrial);
		List<Offer> offerList = new ArrayList<Offer>();
		for(Offer offer:listParentOffer){
			String id = offer.getOfferId();
			offer.setSubOffer(offerService.listOfferByParent(id));
			offerList.add(offer);
		}

		model.addAttribute("billingList", billingList);
		model.addAttribute("offerList", offerList);
		model.addAttribute("ordersDetailMap", ordersDetailMap);
		model.addAttribute("flag", "edit");
		model.addAttribute("customer", orders.getCustomer());
		model.addAttribute("orders", orders);
		model.addAttribute("roleId", user==null?null:user.getRoleId());
		return "orders/orders_add";
	}
	
	/**
	 * 订阅续订
	 * @param detailId
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/renewOrders")
	public ModelAndView renewOrders(String detailId,HttpSession session,HttpServletRequest req){
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = (String) session.getAttribute(Const.SESSION_USER_ID);
		OrdersDetail ordersDetail = ordersService.selectOrdersDetail(detailId);
		Orders orders = ordersService.getOrdersById(ordersDetail.getOrdersId().toString());

		//新建orders
		orders.setId(null);
		Date day=new Date();
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
		orders.setStatus("0");//0:新增;1:已提交;2:已审核。
		orders.setCreateTime(day);
		orders.setCreateUser(userId);
		orders.setType("1");//续订

		BigDecimal discount = null;
		if(user.getRoleId()==3){//如果是总代理商角色，则折扣可以在订单管理界面进行编辑；如果是代理商角色，则查询折扣自动计算。
			Credit credit = creditService.queryCredit(user.getUserId());
			discount = credit ==null?null:credit.getDiscount();
		}
		OfferPrice offerPrice = offerPriceService.getPriceByOfferId(ordersDetail.getOfferId());
		BigDecimal amount = offerPrice.getPriceYear()==null?new BigDecimal(0):offerPrice.getPriceYear().multiply(new BigDecimal(ordersDetail.getQuantity()));
		BigDecimal actualAmount = amount.multiply(discount==null?new BigDecimal(1):discount.divide(new BigDecimal(100)));

		orders.setSum(amount);
		orders.setActualSum(actualAmount);
		orders.setDiscount(discount);
		ordersService.insertOrders(orders);//mybaits数据新增后主键自动更新
		
		//新建订单明细
		ordersDetail.setOriginalId(detailId);
		ordersDetail.setId(null);
		ordersDetail.setOrdersId(orders.getId());
		ordersDetail.setBillingCycle(orders.getBillingCycle());
		ordersDetail.setCreateUser(userId);
		ordersDetail.setAmount(amount.setScale(2));
		ordersDetail.setActualAmount(actualAmount.setScale(2));
		ordersService.insertOrdersDetail(ordersDetail);
		
		//跳转至付款信息界面
		ModelAndView mv = new ModelAndView();
		Map<String, List<OrdersDetail>> ordersDetailMap = getOrdersDetailMap(orders.getId().toString());
		List<PubCode> paymentList = pubCodeService.listPubCodeByClass("PAYMENT");
		HashMap<String,String> statusMap = pubCodeService.codeMapByClass("ORDERSTATUS");
		HashMap<String,String> billingCycleMap = pubCodeService.codeMapByClass("BILLINGCYCLE");
		mv.addObject("orders", orders);
		mv.addObject("ordersDetailMap", ordersDetailMap);
		mv.addObject("paymentList", paymentList);
		mv.addObject("statusMap", statusMap);
		mv.addObject("billingCycleMap", billingCycleMap);
		mv.addObject("roleId", user.getRoleId());
		//返回订单信息
		mv.setViewName("orders/orders_detail");
		return mv;
	}
	
	/**
	 * 添加订单初始化页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/indexOrders")
	public String indexOrders(Model model,HttpSession session){

		User user= (User)session.getAttribute(Const.SESSION_USER);

		List<PubCode> billingList = pubCodeService.listPubCodeByClass("BILLINGCYCLE");
		String isTrial = null;
		
		if(user!=null&&user.getRoleId()!=2){
			isTrial = "0";
		}
		List<Offer> listParentOffer = offerService.listOfferByLevel(1,isTrial);
		List<Offer> offerList = new ArrayList<Offer>();
		for(Offer offer:listParentOffer){
			String id = offer.getOfferId();
			offer.setSubOffer(offerService.listOfferByParent(id));
			offerList.add(offer);
		}
		List<Organ> provinList = organService.listOrganByLevel(1);
		model.addAttribute("provinList", provinList);
		model.addAttribute("billingList", billingList);
		model.addAttribute("offerList", offerList);
		model.addAttribute("roleId", user==null?null:user.getRoleId());
		return "orders/orders_index";
	}

	/**
	 * 保存订单信息（如果用户不存在，则先保存用户信息）
	 * @param orders
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/saveOrders",method=RequestMethod.POST)
	public String saveOrders(Orders orders,Customer customer,HttpSession session,HttpServletRequest req){
		
		String userId = (String) session.getAttribute(Const.SESSION_USER_ID);
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Date day=new Date();
		//Customer customer = orders.getCustomer();//customerService.selectCustomerById(orders.getCustomerId()==null?"":orders.getCustomerId().toString());
		if(orders.getCustomerId()==null){
			customer.setCountry("CN");//默认中国
			customer.setStatus("0");//新增
			customer.setCreateUser(userId);
			customer.setCreateTime(day);
			customerService.insertCustomer(customer);
			orders.setCustomerId(customer.getId());
			Relationship relationship = new Relationship();
			relationship.setCustomerId(customer.getId()==null?"":customer.getId().toString());
			relationship.setUserId(userId);
			relationship.setMpnId(user.getMpnId());
			relationship.setStartTime(day);
			relationship.setEndTime(Tools.str2Date("9999-12-31 23:59:59"));
			relationship.setValid("1");
			customerService.insertRelationship(relationship);
		}
		if(orders.getId()==null||"".equals(orders.getId())){
			
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
			orders.setStatus("0");//0:新增;1:已提交;2:已审核。
			orders.setCreateTime(day);
			orders.setCreateUser(userId);
			ordersService.insertOrders(orders);//mybaits数据新增后主键自动更新
		}else{//如果订单id存在，说明是订单更新操作，需要将订单明细表数据删除后重新添加
			orders = ordersService.getOrdersById(orders.getId().toString());
			ordersService.deleteOrdersDetail(orders.getId().toString());
		}
		String[] checkbox = req.getParameterValues("checkbox");
		String quantity;
		List<OrdersDetail> ordersDetailList = new ArrayList<OrdersDetail>();
		BigDecimal sum = new BigDecimal(0);
		BigDecimal actualSum = new BigDecimal(0);
		BigDecimal discount = null;
		if(user!=null&&user.getRoleId()==3){//如果是总代理商角色，则折扣可以在订单管理界面进行编辑；如果是代理商角色，则查询折扣自动计算。
			Credit credit = creditService.queryCredit(user.getUserId());
			discount = credit ==null?null:credit.getDiscount();
		}
		for(String str:checkbox){
			quantity = req.getParameter("quantity_"+str);
			OrdersDetail ordersDetail = new OrdersDetail();
			ordersDetail.setQuantity(quantity);
			ordersDetail.setOfferId(str);
			ordersDetail.setOrdersId(orders.getId());
			ordersDetail.setBillingCycle(orders.getBillingCycle());
			ordersDetail.setCreateUser(userId);
			OfferPrice offerPrice = offerPriceService.getPriceByOfferId(str);
			BigDecimal amount = offerPrice.getPriceYear()==null?new BigDecimal(0):offerPrice.getPriceYear().multiply(new BigDecimal(quantity));
			BigDecimal actualAmount = amount.multiply(discount==null?new BigDecimal(1):discount.divide(new BigDecimal(100)));
			ordersDetail.setAmount(amount.setScale(2));
			ordersDetail.setActualAmount(actualAmount.setScale(2));
			ordersDetailList.add(ordersDetail);
			sum = sum.add(amount);
			actualSum = actualSum.add(actualAmount);
		}
		orders.setActualSum(actualSum.setScale(2));
		orders.setSum(sum.setScale(2));
		orders.setDiscount(discount);
		ordersService.updateOrders(orders);
		ordersService.insertOrdersDetailList(ordersDetailList);
		return "redirect:getOrders.html?flag=0&id="+orders.getId();
	}
	
	/**
	 * 删除
	 * @param id
	 * @param out
	 */
	@RequestMapping(value="/deleteOrders")
	public void deleteOrders(@RequestParam String id,PrintWriter out){
		Orders orders = ordersService.getOrdersById(id);
		ordersService.deleteOrders(id);
		ordersService.deleteOrdersDetail(id);
		customerService.deleteCustomer(orders.getCustomer());
		out.write("success");
		out.flush();
		out.close();
	}
	
	/**
	 * 删除
	 * @param id
	 * @param out
	 */
	@RequestMapping(value="/checkQuantity")
	public void checkQuantity(HttpServletRequest req,PrintWriter out,HttpSession session){
		JSONObject dataJson = new JSONObject();
		String customerId = req.getParameter("customerId")==null?"":req.getParameter("customerId");
		String offerId = req.getParameter("offerId")==null?"":req.getParameter("offerId");
		String tenantId = req.getParameter("tenantId")==null?"":req.getParameter("tenantId");
		String subscriptionId = req.getParameter("subscriptionId")==null?"":req.getParameter("subscriptionId");
		
		int tmpCount = ordersService.getTotalCount(customerId,offerId);
		int quantity = 0;
		if(!"".equals(tenantId)&&!"".equals(subscriptionId)){//获取21系统中该客户已下订单数量
			String targetURL = RestfulUtil.getBaseUrl()+"/customers/"+tenantId+"/subscriptions/"+subscriptionId;
			JSONObject jsonObject = ordersService.getSubscription(targetURL);
			if(jsonObject.get("responseCode").toString().startsWith("2")){
				JSONObject resultJson = JSONObject.fromObject(jsonObject.get("result"));
				quantity = resultJson.get("quantity")==null||"".equals(resultJson.get("quantity"))?0:resultJson.getInt("quantity");
			}
		}else if(!"".equals(tenantId)){//获取21系统中该客户已下订单数量
			String targetURL = RestfulUtil.getBaseUrl()+"/customers/"+tenantId+"/subscriptions";
			JSONObject resultJson = ordersService.getSubscription(targetURL);
			if(resultJson.get("responseCode").toString().startsWith("2")){
				JSONArray subscriptionArr = JSONArray.fromObject(JSONObject.fromObject(resultJson.get("result")).get("items"));

				for (Object obj : subscriptionArr){
					JSONObject jsonObject = (JSONObject) obj;
					if(offerId.equals(jsonObject.get("offerId"))){
						quantity +=jsonObject.get("quantity")==null||"".equals(jsonObject.get("quantity"))?0:jsonObject.getInt("quantity");
					}
				}
			}
		}
		dataJson.put("tmpCount", tmpCount);
		dataJson.put("quantity", quantity);
		out.write(dataJson.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 增加坐席数量--初始化
	 * @param detailId
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/forIncrease")
	public ModelAndView forIncrease(String id,HttpSession session,HttpServletRequest req){
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Subscription subscription = ordersService.selectSubscription(id);
		Customer customer = customerService.selectCustomerById(subscription.getCustomerId()==null?"":subscription.getCustomerId().toString());
		Offer offer = offerService.getOffer(subscription.getOfferId());
		ModelAndView mv = new ModelAndView();
		mv.addObject("customer", customer);
		mv.addObject("subscription", subscription);
		mv.addObject("roleId", user.getRoleId());
		mv.addObject("offer", offer);
		//返回订单信息
		mv.setViewName("orders/orders_increase");
		return mv;
	}
	
	/**
	 * 增加坐席
	 * @param orders
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/increaseOrders")
	public ModelAndView increaseOrders(HttpSession session,HttpServletRequest req){
		String detailId = req.getParameter("detailId")==null?"":req.getParameter("detailId");
		String quantity = req.getParameter("quantity")==null?"":req.getParameter("quantity");
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = (String) session.getAttribute(Const.SESSION_USER_ID);
		OrdersDetail ordersDetail = ordersService.selectOrdersDetail(detailId);
		Orders orders = ordersService.getOrdersById(ordersDetail.getOrdersId().toString());
		
		//新建orders
		orders.setId(null);
		Date day=new Date();
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
		orders.setStatus("0");//0:新增;1:已提交;2:已审核。
		orders.setCreateTime(day);
		orders.setCreateUser(userId);
		orders.setEffectTime(null);
		orders.setType("2");//增加坐席
		orders.setFile(null);//清除原有附件
		orders.setPayment(null);
		orders.setBillingCycle("monthly");
		
		BigDecimal discount = null;
		if(user.getRoleId()==3){//如果是总代理商角色，则折扣可以在订单管理界面进行编辑；如果是代理商角色，则查询折扣自动计算。
			Credit credit = creditService.queryCredit(user.getUserId());
			discount = credit ==null?null:credit.getDiscount();
		}
		OfferPrice offerPrice = offerPriceService.getPriceByOfferId(ordersDetail.getOfferId());
		BigDecimal amount = offerPrice.getPriceYear()==null?new BigDecimal(0):offerPrice.getPriceYear().multiply(new BigDecimal(quantity));
		BigDecimal actualAmount = amount.multiply(discount==null?new BigDecimal(1):discount.divide(new BigDecimal(100)));

		orders.setSum(amount);
		orders.setActualSum(actualAmount);
		orders.setDiscount(discount);
		ordersService.insertOrders(orders);//mybaits数据新增后主键自动更新
		
		//新建订单明细
		ordersDetail.setId(null);
		ordersDetail.setOriginalId("");
		ordersDetail.setOrdersId(orders.getId());
		ordersDetail.setBillingCycle("monthly");
		ordersDetail.setCreateUser(userId);
		ordersDetail.setQuantity(quantity);
		ordersDetail.setAmount(amount.setScale(2));
		ordersDetail.setActualAmount(actualAmount.setScale(2));
		ordersDetail.setEffectTime(null);
		ordersDetail.setDueTime(null);
		ordersService.insertOrdersDetail(ordersDetail);
		
		//跳转至付款信息界面
		ModelAndView mv = new ModelAndView();
		Map<String, List<OrdersDetail>> ordersDetailMap = getOrdersDetailMap(orders.getId().toString());
		List<PubCode> paymentList = pubCodeService.listPubCodeByClass("PAYMENT");
		HashMap<String,String> statusMap = pubCodeService.codeMapByClass("ORDERSTATUS");
		HashMap<String,String> typeMap = pubCodeService.codeMapByClass("ORDERTYPE");
		HashMap<String,String> billingCycleMap = pubCodeService.codeMapByClass("BILLINGCYCLE");
		mv.addObject("orders", orders);
		mv.addObject("ordersDetailMap", ordersDetailMap);
		mv.addObject("paymentList", paymentList);
		mv.addObject("statusMap", statusMap);
		mv.addObject("typeMap", typeMap);
		mv.addObject("billingCycleMap", billingCycleMap);
		mv.addObject("roleId", user.getRoleId());
		mv.addObject("flag", 0);//新增订单
		//返回订单信息
		mv.setViewName("orders/orders_detail");
		return mv;
	}
	
}
