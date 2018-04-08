package com.office.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.office.entity.Customer;
import com.office.entity.Orders;
import com.office.entity.Subscription;

public interface OrdersService {
	
	/*
	 * 根据父级查询产品列表
	 */
	public List<Orders> listOrdersByCustomer(Customer customer);
	
	/*
	 * 新增订单
	 */
	void insertOrders(Orders orders);
	
	/*
	 * 新增订阅（批量）
	 */
	void insertSubscription(List<Subscription> subscriptionList);
	
	/*
	 * 根据日期查询系统中最大的订单编号
	 */
	public String getMaxOrdersNo(Date date);
	
	/*
	 * 查询订单列表
	 */
	public List<Orders> listOrders(Map<String,Object> map);
	
	/*
	 * 根据订单Id查询基本信息
	 */
	public Orders getOrdersById(String id);
	
	/*
	 * 根据订单Id查询订阅信息
	 */
	public List<Subscription> getSubscription(String ordersId);
	
	/*
	 * 更新订单信息
	 */
	public void updateOrders(Orders orders);
	
	/*
	 * 更新订阅信息
	 */
	public void updateSubscription(Subscription subscription);
}
