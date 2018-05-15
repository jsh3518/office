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

import com.office.entity.Customer;
import com.office.entity.Orders;
import com.office.entity.OrdersDetail;
import com.office.entity.Subscription;
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

	/*
	 * 根据客户查询订单
	 * @see com.office.service.OrdersService#listOrdersByCustomer(com.office.entity.Customer)
	 */
	public List<Orders> listOrdersByCustomer(Customer customer) {
		return ordersMapper.listOrdersByCustomer(customer);
	}
	
	/*
	 * 新增订单
	 * @see com.office.service.OrdersService#insertOrders(com.office.entity.Orders)
	 */
	public void insertOrders(Orders orders) {
		ordersMapper.insertOrders(orders);
	}
	
	/*
	 * 批量新增订单明细
	 * @see com.office.service.OrdersService#insertOrdersDetail(java.util.List)
	 */
	public void insertOrdersDetail(List<OrdersDetail> ordersDetailList) {
		ordersMapper.insertOrdersDetail(ordersDetailList);
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
	public String CreateOrders(Orders orders,String access_token,String imagePath){

		List<OrdersDetail> ordersDetailList = ordersMapper.getOrdersDetail(orders.getId().toString());
		JSONObject orderJson = new JSONObject();
		orderJson.put("referenceCustomerId", orders.getCustomer().getTenantId());
		orderJson.put("billingCycle", orders.getBillingCycle());
		orderJson.put("creationDate", null);
		orderJson.put("attributes", "'objectType': 'order'");
		JSONArray jsonArr = new JSONArray();
		HashMap<String,Subscription> subscriptionMap = new HashMap<String,Subscription>();
		for(int i=0;i<ordersDetailList.size();i++){
			OrdersDetail ordersDetail = ordersDetailList.get(i);
			//TODO 需要根据offerId判断对于商业版产品不允许超过300坐席，且如果微软已存在一条订阅记录，则在原坐席数上增加。
			JSONObject ordersDetailJson = new JSONObject();
			ordersDetailJson.put("lineItemNumber", i);
			ordersDetailJson.put("offerId", ordersDetail.getOfferId());
			ordersDetailJson.put("friendlyName", ordersDetail.getOfferName());
			ordersDetailJson.put("quantity", ordersDetail.getQuantity());
			//此处需要验证MPNID是否有效
			if(orders.getMpnId()!=null){
				JSONObject json = RestfulUtil.getMpnId(access_token,orders.getMpnId());
				if(json.get("responseCode").toString().startsWith("2")){
					ordersDetailJson.put("partnerIdOnRecord",orders.getMpnId());
				}
			}
			ordersDetailJson.put("attributes", "'objectType': 'orderLineItems'");
			jsonArr.add(ordersDetailJson);
			
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
		orderJson.put("lineItems", jsonArr);
		String targetURL = "https://partner.partnercenterapi.microsoftonline.cn/v1/customers/"+orders.getCustomer().getTenantId()+"/orders";
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
		
				orders.setOrderId(orderId);//对应O365订单Id
				orderArr = JSONArray.fromObject(reOrderJson.get("lineItems"));
			}

			List<Subscription>  subscriptionList= new ArrayList<Subscription>();
			for (Object obj : orderArr){
				JSONObject jsonObject = (JSONObject) obj;
				OrdersDetail newOrdersDetail = new OrdersDetail();
				newOrdersDetail.setSubscriptionId(jsonObject.getString("subscriptionId"));
				newOrdersDetail.setOfferId(jsonObject.getString("offerId"));
				newOrdersDetail.setOrdersId(orders.getId());
				newOrdersDetail.setBillingCycle(orders.getBillingCycle());
				ordersMapper.updateOrdersDetail(newOrdersDetail);

				//将数据写入订阅信息表
				Subscription subscription = (Subscription)subscriptionMap.get(jsonObject.getString("offerId"));
				if(subscription!=null){
					subscription.setSubscriptionId(jsonObject.getString("subscriptionId"));
					subscription.setEffectTime(new Date());
					subscriptionList.add(subscription);
				}

			}
			ordersMapper.insertSubscriptionList(subscriptionList);
			orders.setEffectTime(new Date());
			ordersMapper.updateOrders(orders);
			// 发送邮件
			Mail mail = new Mail();
			Multipart multipart = mail.setContect(orders.getCustomer(), imagePath, "orders");
			mail.sendMail(orders.getCustomer().getEmail(),"欢迎使用office365", multipart);
			return "success";
		}else{
			return "failed";
		}
	}
	
	/*
	 * 坐席续订
	 * @see com.office.service.OrdersService#renewOrders(com.office.entity.Orders, java.lang.String)
	 */
	public String renewOrders(Orders orders,String access_token){
		List<OrdersDetail> ordersDetailList = ordersMapper.getOrdersDetail(orders.getId().toString());
		for(int i=0;i<ordersDetailList.size();i++){
			OrdersDetail ordersDetail = ordersDetailList.get(i);
			ordersMapper.updateRenew(ordersDetail.getOriginalId(),12);
			//TODO 需要往微软写数据
			Orders tmpOrders = new Orders();
			tmpOrders.setId(orders.getId());
			tmpOrders.setStatus("2");//已审核
			tmpOrders.setBillingCycle(orders.getBillingCycle());
			ordersMapper.updateOrders(tmpOrders);
		}
		return "success";
	}
}
