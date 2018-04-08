package com.office.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.entity.Customer;
import com.office.entity.Orders;
import com.office.entity.Subscription;
import com.office.mapper.OrdersMapper;
import com.office.service.OrdersService;

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
	 * 批量新增订阅
	 * @see com.office.service.OrdersService#insertSubscription(java.util.List)
	 */
	public void insertSubscription(List<Subscription> subscriptionList) {
		ordersMapper.insertSubscription(subscriptionList);
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
	 * 根据订单Id查询订阅信息
	 * @see com.office.service.OrdersService#getSubscription(java.lang.String)
	 */
	public List<Subscription> getSubscription(String OrdersId){
		return ordersMapper.getSubscription(OrdersId);
	}
	
	/*
	 * 更新订单信息
	 * @see com.office.service.OrdersService#updateOrders(com.office.entity.Orders)
	 */
	public void updateOrders(Orders orders){
		ordersMapper.updateOrders(orders);
	}
	
	/*
	 * 更新订阅信息
	 * @see com.office.service.OrdersService#updateSubscription(com.office.entity.Subscription)
	 */
	public void updateSubscription(Subscription subscription){
		ordersMapper.updateSubscription(subscription);
	}
}
