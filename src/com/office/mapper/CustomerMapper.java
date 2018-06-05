package com.office.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.office.entity.Customer;
import com.office.entity.Relationship;

public interface CustomerMapper  {

	public List<Customer> listPageCustomer(Customer customer);
	
	public List<Customer> listPageCustomerQry(HashMap<String, Object> map);
	
	public void insertCustomer(Customer customer);
	
	public Customer selectCustomerById(String id);
	
	public Customer getCustomerDomain(String domain,String customerId);
	
	public void updateCustomer(Customer customer);
	
	public void updateTenantId(@Param("id")int id,@Param("tenantId")String tenantId,@Param("password")String password,@Param("status")String status);
	
	public void insertRelationship(Relationship relationship);
	
	public int getOrdersCount(int customerId);
	
	public void deleteCustomer(int id);
	
	public void deleteRelationship(int customerId);
}
