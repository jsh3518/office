package com.office.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.office.entity.Customer;
import com.office.entity.Orders;
import com.office.entity.OrdersDetail;
import com.office.entity.Subscription;

public interface OrdersMapper  {
	
	public List<Orders> listOrdersByCustomer(Customer customer);
	
	public void insertOrders(Orders orders);
	
	public void insertOrdersDetail(List<OrdersDetail> ordersDetailList);
	
	public void insertSubscription(Subscription subscription);
	
	public String getMaxOrdersNo(Date date);
	
	public List<Orders> listPageOrders(Map<String,Object> map);
	
	public Orders getOrdersById(String id);
	
	public List<OrdersDetail> getOrdersDetail(String ordersId);
	
	public OrdersDetail selectOrdersDetail(String detailId);
	
	public void updateOrders(Orders orders);
	
	public void updateOrdersDetail(OrdersDetail ordersDetail);
	
	public void updateSubscription(Subscription subscription);
	
	public void deleteOrdersDetail(String ordersId);
	
	public List<Object> listPageSubscription(Map<String,Object> map);
}
