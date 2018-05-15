package com.office.service;

import java.util.List;

import com.office.entity.Customer;

public interface CustomerService {
	/*
	 * 查询客户列表
	 */
	public List<Customer> listCustomer(Customer customer);
	/*
	 * 根据id查询客户
	 */
	public Customer selectCustomerById(String id);
	/*
	 * 新增客户
	 */
	public void insertCustomer(Customer customer);
	/*
	 * 更新客户
	 */
	public void updateCustomer(Customer customer);
	/*
	 * 更新Office客户Id
	 */
	public void updateTenantId(int id,String tenantId,String password,String status);
	/*
	 * 在微软新增客户信息
	 */
	public Customer saveCustomer(Customer customer,String access_token,String imagePath);
}
