package com.office.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.office.dao.OrdersDao;
import com.office.entity.Customer;
import com.office.entity.Orders;

/**
 * @author jiashouhui
 * 修改时间：2018-03-21
 */
@Repository("OrdersDaoImpl")
public class OrdersDaoImpl extends BaseDaoImpl implements OrdersDao {

	@Override
	public List<Orders> listOrdersByCustomer(Customer customer) throws Exception {
		return (List<Orders>)findForList("Orders.listOrders", customer);
		
	}

	@Override
	public void insertOrders(Orders orders) throws Exception {
		save("Orders.insertOrders", orders);
		
	}


}


