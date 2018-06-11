package com.office.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.Multipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.entity.Offer;
import com.office.entity.Orders;
import com.office.entity.OrdersDetail;
import com.office.entity.Subscription;
import com.office.mapper.OfferMapper;
import com.office.mapper.OrdersMapper;
import com.office.service.OrdersService;
import com.office.util.Mail;
import com.office.util.RestfulUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("OrdersService")
public class OrdersServiceImpl implements OrdersService {
	@Resource(name="ordersMapper")
	@Autowired
	private OrdersMapper ordersMapper;
	@Resource(name="offerMapper")
	@Autowired
	private OfferMapper offerMapper;

	/*
	 * 新增订单
	 * @see com.office.service.OrdersService#insertOrders(com.office.entity.Orders)
	 */
	public void insertOrders(Orders orders) {
		ordersMapper.insertOrders(orders);
	}
	
	/*
	 * 新增订单明细
	 * @see com.office.service.OrdersService#insertOrdersDetail(com.office.entity.OrdersDetail)
	 */
	public void insertOrdersDetail(OrdersDetail ordersDetail) {
		ordersMapper.insertOrdersDetail(ordersDetail);
	}
	
	/*
	 * 批量新增订单明细
	 * @see com.office.service.OrdersService#insertOrdersDetail(java.util.List)
	 */
	public void insertOrdersDetailList(List<OrdersDetail> ordersDetailList) {
		ordersMapper.insertOrdersDetailList(ordersDetailList);
	}
	
	/*
	 * 新增订阅
	 * @see com.office.service.OrdersService#insertSubscription(com.office.entity.Subscription)
	 */
	public void insertSubscription(Subscription subscription) {
		ordersMapper.insertSubscription(subscription);
	}
	
	/*
	 * 批量新增订阅
	 * @see com.office.service.OrdersService#insertSubscriptionList(java.util.List)
	 */
	public void insertSubscriptionList(List<Subscription> subscriptionList) {
		ordersMapper.insertSubscriptionList(subscriptionList);
	}
	
	/*
	 * 根据日期查询系统中最大的订单编号
	 * @see com.office.service.OrdersService#getMaxOrdersNo(java.sql.Date)
	 */
	public String getMaxOrdersNo(Date date){
		return ordersMapper.getMaxOrdersNo(date);
	}
	
	/*
	 * 查询订单列表
	 * @see com.office.service.OrdersService#listOrders(java.util.Map)
	 */
	public List<Orders> listOrders(Map<String,Object> map){
		return ordersMapper.listPageOrders(map);
	}
	
	/*
	 * 查询订单信息
	 * @see com.office.service.OrdersService#getOrdersById(java.lang.String)
	 */
	public Orders getOrdersById(String id){
		return ordersMapper.getOrdersById(id);
	}
	
	/*
	 * 根据订单Id查询订单明细信息
	 * @see com.office.service.OrdersService#getOrdersDetail(java.lang.String)
	 */
	public List<OrdersDetail> getOrdersDetail(String ordersId){
		return ordersMapper.getOrdersDetail(ordersId);
	}
	
	/*
	 * 根据订单明细Id查询订单明细信息
	 * @see com.office.service.OrdersService#selectOrdersDetail(java.lang.String)
	 */
	public OrdersDetail selectOrdersDetail(String detailId){
		return ordersMapper.selectOrdersDetail(detailId);
	}
	
	/*
	 * 根据订阅Id查询订阅信息
	 * @see com.office.service.OrdersService#selectSubscription(java.lang.String)
	 */
	public Subscription selectSubscription(String id){
		return ordersMapper.selectSubscription(id);
	}
	
	/*
	 * 更新订单信息
	 * @see com.office.service.OrdersService#updateOrders(com.office.entity.Orders)
	 */
	public void updateOrders(Orders orders){
		ordersMapper.updateOrders(orders);
	}
	
	/*
	 * 更新订单明细信息
	 * @see com.office.service.OrdersService#updateOrdersDetail(com.office.entity.OrdersDetail)
	 */
	public void updateOrdersDetail(OrdersDetail ordersDetail){
		ordersMapper.updateOrdersDetail(ordersDetail);
	}

	/*
	 * 更新订阅信息
	 * @see com.office.service.OrdersService#updateSubscription(com.office.entity.Subscription)
	 */
	public void updateSubscription(Subscription subscription){
		ordersMapper.updateSubscription(subscription);
	}
	
	/*
	 * 根据订单id删除明细信息
	 * @see com.office.service.OrdersService#deleteOrders(java.lang.String)
	 */
	public void deleteOrders(String ordersId){
		ordersMapper.deleteOrders(ordersId);
	}
	
	/*
	 * 根据订单id删除明细信息
	 * @see com.office.service.OrdersService#deleteOrdersDetail(java.lang.String)
	 */
	public void deleteOrdersDetail(String ordersId){
		ordersMapper.deleteOrdersDetail(ordersId);
	}
	
	/*
	 * 查询订阅信息
	 * @see com.office.service.OrdersService#listSubscription(java.util.Map)
	 */
	public List<Object> listSubscription(Map<String,Object> map) {
		return ordersMapper.listPageSubscription(map);
	}
	
	/*
	 * 在微软创建订单信息(订阅新增)
	 * @see com.office.service.OrdersService#CreateOrders(com.office.entity.Orders, java.lang.String, java.lang.String)
	 */
	public String CreateOrders(Orders orders,String imagePath){

		List<OrdersDetail> ordersDetailList = ordersMapper.getOrdersDetail(orders.getId().toString());
		List<Subscription>  subscriptionList= new ArrayList<Subscription>();
		String access_token = RestfulUtil.getAccessToken();
		JSONObject orderJson = new JSONObject();
		orderJson.put("referenceCustomerId", orders.getCustomer().getTenantId());
		orderJson.put("billingCycle", orders.getBillingCycle());
		orderJson.put("creationDate", null);
		orderJson.put("attributes", "'objectType': 'order'");
		JSONArray jsonArr = new JSONArray();
		String mpnId = orders.getMpnId();
		//此处需要验证MPNID是否有效
		if(mpnId!=null&&!"".equals(mpnId)){
			JSONObject json = RestfulUtil.getMpnId(mpnId);
			if(!json.get("responseCode").toString().startsWith("2")){
				mpnId = "";
			}
		}
		//商业版产品信息map
		HashMap<String,Offer> offerMap = new HashMap<String,Offer>();
		List<Offer> offerList = offerMapper.listOfferByParent("Business");
		for(int i=0;i<offerList.size();i++){
			Offer offer = offerList.get(i);
			if(offer!=null&&offer.getOfferId()!=null){
				offerMap.put(offer.getOfferId(), offer);
			}
		}
		//对于商业版产品，如果21V中已存在一条订阅信息，则不新增订阅，只是在原有订阅上增加坐席数量
		//构建21V系统中已订阅商业版产品map信息，包含产品Id，订阅Id，订阅数量等。组装形式  key：产品Id；value：hashMap
		HashMap<String,Object> businessMap = null;
		HashMap<String,Subscription> subscriptionMap = new HashMap<String,Subscription>();
		//定义需要更新的订阅信息数组（已有订阅信息的商业版产品直接更新订阅数量）
		JSONArray updateArr = new JSONArray();
		int k = 0 ;
		for(int i=0;i<ordersDetailList.size();i++){
			OrdersDetail ordersDetail = ordersDetailList.get(i);
			
			JSONObject ordersDetailJson = new JSONObject();
			ordersDetailJson.put("lineItemNumber", k);
			ordersDetailJson.put("offerId", ordersDetail.getOfferId());
			ordersDetailJson.put("friendlyName", ordersDetail.getOfferName());
			ordersDetailJson.put("quantity", ordersDetail.getQuantity());
			//if(mpnId!=null&&!"".equals(mpnId)){
				ordersDetailJson.put("partnerIdOnRecord",mpnId);
			//}
			ordersDetailJson.put("attributes", "'objectType': 'orderLineItems'");
			
			String offerId = ordersDetail.getOfferId()==null?"":ordersDetail.getOfferId().toString();
			//根据offerId判断是否为商业版产品。一个客户商业版产品只允许有一条订阅信息，最大坐席数量不超过300。如果微软已存在一条订阅记录，则在原坐席数上增加。
			//如果存在商业版产品订阅，则将信息放入map中
			if(!"".equals(offerId)&&offerMap.containsKey(ordersDetail.getOfferId())){
				Offer offer = (Offer)offerMap.get(offerId);
				int maxinum = Integer.valueOf(offer.getMaxinum());
				int num = ordersDetail.getQuantity()==null?0:Integer.valueOf(ordersDetail.getQuantity().toString());
				if(num>maxinum){
					return  ordersDetail.getOfferName()+"最大允许订阅坐席数量为"+maxinum+"，请修改数量！" ;
				}else{
					int quantity = 0;
					if(businessMap==null){
						businessMap = getSubscribeMap( offerMap,orders.getCustomer().getTenantId());
					}
					//已存在该商业版产品的订阅，调用接口修改坐席数量(将信息存入updateArr，后续统一调用，防止中间出错),结束本次循环，进入下一次循环
					if(businessMap.containsKey(offerId)){
						JSONObject subscriptionJson = (JSONObject)businessMap.get(offerId);
						quantity = subscriptionJson.getInt("quantity");
						if(quantity+num>maxinum){
							return ordersDetail.getOfferName()+"最大允许订阅坐席数量为"+maxinum+"，已有订阅"+quantity+"。请修改数量！" ;
						}else{
							quantity = quantity+num ;
							subscriptionJson.put("quantity",quantity);
							updateArr.add(subscriptionJson);
							//更新订单明细信息
							OrdersDetail newOrdersDetail = new OrdersDetail();
							newOrdersDetail.setSubscriptionId(subscriptionJson.getString("id"));
							newOrdersDetail.setOfferId(offerId);
							newOrdersDetail.setOrdersId(orders.getId());
							//newOrdersDetail.setBillingCycle(orders.getBillingCycle());
							newOrdersDetail.setEffectTime(new Date());
							ordersMapper.updateOrdersDetail(newOrdersDetail);
							//保存至订阅信息表（存入subscriptionMap，后续统一写数据库）
							Subscription subscription = new Subscription();
							subscription.setCustomerId(orders.getCustomer().getId());
							subscription.setDetailId(ordersDetail.getId());
							subscription.setOfferId(ordersDetail.getOfferId());
							subscription.setOfferName(ordersDetail.getOfferName());
							subscription.setQuantity(ordersDetail.getQuantity());
							subscription.setRenew(0);//续订时长为0
							subscription.setBillingCycle(ordersDetail.getBillingCycle());
							subscription.setMpnId(orders.getMpnId());
							subscription.setReseller(orders.getReseller());
							subscription.setCreateUser(ordersDetail.getCreateUser());
							subscription.setSubscriptionId(subscriptionJson.getString("id"));
							//subscriptionMap.put(ordersDetail.getOfferId(), subscription);
							subscriptionList.add(subscription);
							//结束本次循环，进入下一次循环
							continue;
						}
					}
				}
			}

			jsonArr.add(ordersDetailJson);
			k++;
			Subscription subscription = new Subscription();
			subscription.setCustomerId(orders.getCustomer().getId());
			subscription.setDetailId(ordersDetail.getId());
			subscription.setOfferId(ordersDetail.getOfferId());
			subscription.setOfferName(ordersDetail.getOfferName());
			subscription.setQuantity(ordersDetail.getQuantity());
			subscription.setRenew(0);//续订时长为0
			subscription.setBillingCycle(ordersDetail.getBillingCycle());
			subscription.setMpnId(orders.getMpnId());
			subscription.setReseller(orders.getReseller());
			subscription.setCreateUser(ordersDetail.getCreateUser());
			subscriptionMap.put(ordersDetail.getOfferId(), subscription);

		}
		if(jsonArr.size()>0){
			orderJson.put("lineItems", jsonArr);
			String targetURL = RestfulUtil.getBaseUrl()+"/customers/"+orders.getCustomer().getTenantId()+"/orders";
			String method = "POST";
			Map<String, String> paramHeader = new HashMap<String, String>();
			paramHeader.put("Accept", "application/json");
			paramHeader.put("Content-Type", "application/json");
			paramHeader.put("Authorization",  "Bearer "+access_token);
			String paramBody = orderJson.toString();     
			JSONObject resultJson = RestfulUtil.getPartnerCenterData(targetURL,method,paramHeader,paramBody);
			if(resultJson.get("responseCode").toString().startsWith("2")){
				//2018-04-02 该接口返回值前有一个特殊符号，需要截取掉再转换成json
				JSONObject reOrderJson = JSONObject.fromObject(resultJson.get("result"));
				String orderId = reOrderJson.get("id")==null?"":reOrderJson.get("id").toString();
				orders.setOrderId(orderId);//对应O365订单Id
				JSONArray orderArr = JSONArray.fromObject(reOrderJson.get("lineItems"));
	
				for (Object obj : orderArr){
					JSONObject jsonObject = (JSONObject) obj;
					OrdersDetail newOrdersDetail = new OrdersDetail();
					newOrdersDetail.setSubscriptionId(jsonObject.getString("subscriptionId"));
					newOrdersDetail.setOfferId(jsonObject.getString("offerId"));
					newOrdersDetail.setOrdersId(orders.getId());
					newOrdersDetail.setBillingCycle(orders.getBillingCycle());
					newOrdersDetail.setEffectTime(new Date());
					
					ordersMapper.updateOrdersDetail(newOrdersDetail);
	
					//将数据写入订阅信息表
					Subscription subscription = (Subscription)subscriptionMap.get(jsonObject.getString("offerId"));
					if(subscription!=null){
						subscription.setSubscriptionId(jsonObject.getString("subscriptionId"));
						subscription.setEffectTime(new Date());
						subscriptionList.add(subscription);
					}
	
				}
			}else{
				return "审核失败！";
			}
		}
		// 已订阅商业版产品修改订阅数量
		for (Object object : updateArr){
			JSONObject updateJSON = (JSONObject) object;
			String targetURL = RestfulUtil.getBaseUrl()+"/customers/"+orders.getCustomer().getTenantId()+"/subscriptions/"+updateJSON.get("id");
			String method = "PATCH";
			Map<String, String> paramHeader = new HashMap<String, String>();
			paramHeader.put("Accept", "application/json");
			paramHeader.put("Content-Type", "application/json");
			paramHeader.put("Authorization",  "Bearer "+access_token); 
			RestfulUtil.getPartnerCenterData(targetURL,method,paramHeader,updateJSON.toString());
			
		}
		ordersMapper.insertSubscriptionList(subscriptionList);
		orders.setEffectTime(new Date());
		ordersMapper.updateOrders(orders);
		// 发送邮件
		Mail mail = new Mail();
		Multipart multipart = mail.setContect(orders.getCustomer(), imagePath, "orders");
		mail.sendMail(orders.getCustomer().getEmail(),"欢迎使用office365", multipart);
		return "success";
	}
	
	/*
	 * 坐席续订
	 * @see com.office.service.OrdersService#renewOrders(com.office.entity.Orders, java.lang.String)
	 */
	public String renewOrders(Orders orders){
		List<OrdersDetail> ordersDetailList = ordersMapper.getOrdersDetail(orders.getId().toString());
		for(int i=0;i<ordersDetailList.size();i++){
			OrdersDetail ordersDetail = ordersDetailList.get(i);
			ordersMapper.updateSubscriptionRenew(ordersDetail.getOriginalId(),12);
			ordersMapper.updateDetailRenew(ordersDetail.getOriginalId(),12);
			//TODO 需要往微软写数据
		}
		Orders tmpOrders = new Orders();
		tmpOrders.setId(orders.getId());
		tmpOrders.setStatus("2");//已审核
		tmpOrders.setBillingCycle(orders.getBillingCycle());
		ordersMapper.updateOrders(tmpOrders);
		return "success";
	}
	
	/*
	 * 增加坐席
	 * @see com.office.service.OrdersService#increaseOrders(com.office.entity.Orders)
	 */
	public String increaseOrders(Orders orders){
		List<OrdersDetail> ordersDetailList = ordersMapper.getOrdersDetail(orders.getId().toString());
		List<Subscription>  subscriptionList= new ArrayList<Subscription>();
		Date date = new Date();
		for(int i=0;i<ordersDetailList.size();i++){
			OrdersDetail ordersDetail = ordersDetailList.get(i);
			
			int num = ordersDetail.getQuantity()==null?0:Integer.valueOf(ordersDetail.getQuantity().toString());
			String access_token = RestfulUtil.getAccessToken();
			String targetURL = RestfulUtil.getBaseUrl()+"/customers/"+orders.getCustomer().getTenantId()+"/subscriptions/"+ordersDetail.getSubscriptionId();
			String method = "GET";
			Map<String, String> paramHeader = new HashMap<String, String>();
			paramHeader.put("Accept", "application/json");
			paramHeader.put("Content-Type", "application/json");
			paramHeader.put("Authorization",  "Bearer "+access_token);    
			JSONObject jsonObject = RestfulUtil.getPartnerCenterData(targetURL,method,paramHeader,null);
			int quantity = 0;
			if(jsonObject.get("responseCode").toString().startsWith("2")){
				JSONObject json = JSONObject.fromObject(jsonObject.get("result"));
				quantity = json.get("quantity")==null||"".equals(json.get("quantity"))?0:Integer.parseInt(json.get("quantity").toString());
			}
			JSONObject newJson = new JSONObject();
			newJson.put("id", ordersDetail.getSubscriptionId());
			newJson.put("quantity",num+quantity);
			newJson.put("orderId", orders.getOrderId());
			method = "PATCH";
			JSONObject resultJson = RestfulUtil.getPartnerCenterData(targetURL,method,paramHeader,newJson.toString());
			if(resultJson.get("responseCode").toString().startsWith("2")){
				Subscription subscription = new Subscription();
				subscription.setCustomerId(orders.getCustomer().getId());
				subscription.setDetailId(ordersDetail.getId());
				subscription.setOfferId(ordersDetail.getOfferId());
				subscription.setOfferName(ordersDetail.getOfferName());
				subscription.setQuantity(ordersDetail.getQuantity());
				subscription.setRenew(0);//续订时长为0
				subscription.setBillingCycle(ordersDetail.getBillingCycle());
				subscription.setMpnId(orders.getMpnId());
				subscription.setReseller(orders.getReseller());
				subscription.setCreateUser(ordersDetail.getCreateUser());
				subscription.setEffectTime(date);
				subscriptionList.add(subscription);
				OrdersDetail tmpDetail = new OrdersDetail();
				tmpDetail.setId(ordersDetail.getId());
				tmpDetail.setEffectTime(date);
				ordersMapper.updateOrdersDetail(tmpDetail);
			}else{
				return "审核失败！";
			}
		}
		Orders tmpOrders = new Orders();
		tmpOrders.setId(orders.getId());
		tmpOrders.setStatus("2");//已审核
		tmpOrders.setEffectTime(date);
		ordersMapper.updateOrders(tmpOrders);
		ordersMapper.insertOrdersDetailList(ordersDetailList);
		return "success";
	}
	
	/*
	 * 根据客户Id和产品id查询订阅坐席数量
	 * @see com.office.service.OrdersService#getTotalCount(java.lang.String, java.lang.String)
	 */
	public int getTotalCount(String customerId,String offerId){
		return ordersMapper.getTotalCount(customerId,offerId);
	}
	
	//构建21V系统中已订阅商业版产品map信息，包含产品Id，订阅Id，订阅数量等。组装形式  key：产品Id；value：hashMap
	public HashMap<String,Object> getSubscribeMap(HashMap<String,Offer> offerMap,String tenantId){
		HashMap<String,Object> subscriptionMap = new HashMap<String,Object>();
		String access_token = RestfulUtil.getAccessToken();
		String targetURL = RestfulUtil.getBaseUrl()+"/customers/"+tenantId+"/subscriptions";
		String method = "GET";
		Map<String, String> paramHeader = new HashMap<String, String>();
		paramHeader.put("Accept", "application/json");
		paramHeader.put("Content-Type", "application/json");
		paramHeader.put("Authorization",  "Bearer "+access_token);
		JSONObject resultJson = RestfulUtil.getPartnerCenterData(targetURL,method,paramHeader,null);
		if(resultJson.get("responseCode").toString().startsWith("2")){
			JSONObject subscriptionJson = (JSONObject)(resultJson.get("result"));
			JSONArray subscriptionArr = JSONArray.fromObject(subscriptionJson.get("items"));
			//将商业版产品订阅信息存入Map中（规定：商业版产品，一个客户只允许有一条订阅信息，最大订阅数量为300坐席）
			for (Object obj : subscriptionArr){
				JSONObject jsonObject = (JSONObject) obj;
				if(offerMap.containsKey(jsonObject.get("offerId"))){
					subscriptionMap.put(jsonObject.getString("offerId"), jsonObject);
				}
			}
		}
		return subscriptionMap;
	}
	
	//从21V查询订阅信息
	public JSONObject getSubscription(String targetURL){
		String access_token = RestfulUtil.getAccessToken();
		String method = "GET";
		Map<String, String> paramHeader = new HashMap<String, String>();
		paramHeader.put("Accept", "application/json");
		paramHeader.put("Content-Type", "application/json");
		paramHeader.put("Authorization",  "Bearer "+access_token);
		return RestfulUtil.getPartnerCenterData(targetURL,method,paramHeader,null);
	}
}
