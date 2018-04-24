package com.office.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.office.entity.Customer;

public interface CustomerMapper  {

	public List<Customer> listPageCustomer(Customer customer);
	
	public void insertCustomer(Customer customer);
	
	public Customer selectCustomerById(String id);
	
	public void updateCustomer(Customer customer);
	
	public void updateTenantId(@Param("id")int id,@Param("tenantId")String tenantId,@Param("password")String password);
}
