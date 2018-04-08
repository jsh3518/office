package com.office.service;

import java.util.List;
import com.office.entity.Customer;

public interface CustomerService {
	/*
	 * 查询用户列表
	 */
	public List<Customer> listCustomer(Customer customer);
	/*
	 * 根据id查询用户
	 */
	public Customer selectCustomerById(String id);
	/*
	 * 新增用户列表
	 */
	public void insertCustomer(Customer customer);
	/*
	 * 更新用户
	 */
	public void updateTenantId(int id,String tenantId);
}
