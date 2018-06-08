package com.office.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.office.entity.Orders;
import com.office.entity.OrdersDetail;
import com.office.entity.Subscription;

public interface OrdersMapper  {
	
	public void insertOrders(Orders orders);
	
	public void insertOrdersDetail(OrdersDetail ordersDetail);
	
	public void insertOrdersDetailList(List<OrdersDetail> ordersDetailList);
	
	public void insertSubscription(Subscription subscription);
	
	public void insertSubscriptionList(List<Subscription> subscriptionList);
	
	public String getMaxOrdersNo(Date date);
	
	public List<Orders> listPageOrders(Map<String,Object> map);
	
	public Orders getOrdersById(String id);
	
	public List<OrdersDetail> getOrdersDetail(String ordersId);
	
	public OrdersDetail selectOrdersDetail(String detailId);
	
	public Subscription selectSubscription(String id);
	
	public void updateOrders(Orders orders);
	
	public void updateOrdersDetail(OrdersDetail ordersDetail);
	
	public void updateSubscription(Subscription subscription);
	
	public void deleteOrders(String ordersId);
	
	public void deleteOrdersDetail(String ordersId);
	
	public List<Object> listPageSubscription(Map<String,Object> map);
	
	public void updateSubscriptionRenew(@Param("detailId")String detailId,@Param("renew")int renew);
	
	public void updateDetailRenew(@Param("detailId")String detailId,@Param("renew")int renew);
	
	public int getTotalCount(String customerId,String offerId);
}
