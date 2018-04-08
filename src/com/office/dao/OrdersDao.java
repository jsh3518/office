package com.office.dao;

import java.util.List;

import com.office.entity.Customer;
import com.office.entity.Orders;

/**
 * @author jiashouhui
 * 修改时间：2018-03-21
 */
public interface OrdersDao {
	
	/**
	 * 根据客户查询订单
	 * @param customer
	 * @return
	 * @throws Exception 
	 */
	List<Orders> listOrdersByCustomer(Customer customer) throws Exception;
	
	/**
	 * 新增订单信息
	 * @param orders
	 * @throws Exception 
	 */
	public void insertOrders(Orders orders) throws Exception;
}
