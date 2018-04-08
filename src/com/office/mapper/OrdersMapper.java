package com.office.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.office.entity.Customer;
import com.office.entity.Orders;
import com.office.entity.Subscription;

public interface OrdersMapper  {
	
	public List<Orders> listOrdersByCustomer(Customer customer);
	
	void insertOrders(Orders orders);
	
	void insertSubscription(List<Subscription> subscriptionList);
	
	public String getMaxOrdersNo(Date date);
	
	public List<Orders> listPageOrders(Map<String,Object> map);
	
	public Orders getOrdersById(String id);
	
	public List<Subscription> getSubscription(String ordersId);
	
	public void updateOrders(Orders orders);
	
	public void updateSubscription(Subscription subscription);
}
