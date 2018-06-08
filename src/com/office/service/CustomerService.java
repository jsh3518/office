package com.office.service;

import java.util.HashMap;
import java.util.List;

import com.office.entity.Customer;
import com.office.entity.Relationship;

public interface CustomerService {
	/*
	 * 查询客户列表
	 */
	public List<Customer> listCustomer(Customer customer);
	
	/*
	 * 查询客户列表
	 */
	public List<Customer> queryCustomer(HashMap<String, Object> map);
	/*
	 * 根据id查询客户
	 */
	public Customer selectCustomerById(String id);
	/*
	 * 根据domain查询客户
	 */
	public Customer getCustomerDomain(String domain,String customerId);
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
	public Customer saveCustomer(Customer customer,String imagePath);
	/*
	 * 新增客户经销商关系
	 */
	public void insertRelationship(Relationship relationship);
	/*
	 * 删除客户
	 */
	public String deleteCustomer(Customer customer);
}
