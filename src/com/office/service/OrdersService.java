package com.office.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.office.entity.Customer;
import com.office.entity.Orders;
import com.office.entity.OrdersDetail;
import com.office.entity.Subscription;

public interface OrdersService {
	
	/*
	 * 查询订单列表
	 */
	public List<Orders> listOrdersByCustomer(Customer customer);
	
	/*
	 * 新增订单
	 */
	public void insertOrders(Orders orders);
	
	/*
	 * 新增订单明细（批量）
	 */
	void insertOrdersDetail(List<OrdersDetail> ordersDetailList);
	
	
	/*
	 * 新增订阅
	 */
	public void insertSubscription(Subscription subscription);
	
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
	public List<OrdersDetail> getOrdersDetail(String ordersId);
	
	/*
	 * 根据订单明细Id查询订阅信息
	 */
	public OrdersDetail selectOrdersDetail(String detailId);
	
	/*
	 * 更新订单信息
	 */
	public void updateOrders(Orders orders);
	
	/*
	 * 更新订单明细信息
	 */
	public void updateOrdersDetail(OrdersDetail ordersDetail);
	
	/*
	 * 更新订阅信息
	 */
	public void updateSubscription(Subscription subscription);
	
	/*
	 * 根据订单id删除明细信息
	 */
	public void deleteOrdersDetail(String ordersId);
	
	/*
	 * 查询订阅信息列表
	 */
	public List<Object> listSubscription(Map<String,Object> map);
}
