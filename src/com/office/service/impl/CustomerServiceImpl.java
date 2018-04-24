package com.office.service.impl;

import java.util.List;

import com.office.entity.Customer;
import com.office.mapper.CustomerMapper;
import com.office.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

	private CustomerMapper customerMapper;
	
	public CustomerMapper getCustomerMapper() {
		return customerMapper;
	}

	public void setCustomerMapper(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}
	
	/*
	 * 查询客户列表
	 * @see com.office.service.CustomerService#listCustomer(com.office.entity.Customer)
	 */
	public List<Customer> listCustomer(Customer customer) {
		return customerMapper.listPageCustomer(customer);
	}
	
	/*
	 * 根据id查询客户
	 * @see com.office.service.CustomerService#selectCustomerById(java.lang.String)
	 */
	public Customer selectCustomerById(String id){
		return customerMapper.selectCustomerById(id);
	}
	/*
	 * 新增客户
	 * @see com.office.service.CustomerService#insertCustomer(com.office.entity.Customer)
	 */
	public void insertCustomer(Customer customer) {
		customerMapper.insertCustomer(customer);
	}
	/*
	 * 更新客户
	 * @see com.office.service.CustomerService#updateCustomer(com.office.entity.Customer)
	 */
	public void updateCustomer(Customer customer) {
		customerMapper.updateCustomer(customer);
	}
	/*
	 * 更新客户id
	 * @see com.office.service.CustomerService#updateTenantId(int, java.lang.String, java.lang.String)
	 */
	public void updateTenantId(int id,String tenantId,String password){
		customerMapper.updateTenantId(id,tenantId,password);
	}
}
