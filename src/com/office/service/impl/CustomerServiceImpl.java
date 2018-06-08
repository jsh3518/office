package com.office.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Multipart;

import com.office.entity.Customer;
import com.office.entity.Relationship;
import com.office.mapper.CustomerMapper;
import com.office.service.CustomerService;
import com.office.util.AesUtil;
import com.office.util.Mail;
import com.office.util.RestfulUtil;

import net.sf.json.JSONObject;

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
	 * 查询客户列表
	 * @see com.office.service.CustomerService#queryCustomer(java.util.HashMap)
	 */
	public List<Customer> queryCustomer(HashMap<String, Object> map) {
		return customerMapper.listPageCustomerQry(map);
	}
	
	/*
	 * 根据id查询客户
	 * @see com.office.service.CustomerService#selectCustomerById(java.lang.String)
	 */
	public Customer selectCustomerById(String id){
		return customerMapper.selectCustomerById(id);
	}
	
	/*
	 * 根据domain查询客户
	 * @see com.office.service.CustomerService#getCustomerDomain(java.lang.String,java.lang.String)
	 */
	public Customer getCustomerDomain(String domain,String customerId){
		return customerMapper.getCustomerDomain(domain,customerId);
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
	 * @see com.office.service.CustomerService#updateTenantId(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateTenantId(int id,String tenantId,String password,String status){
		customerMapper.updateTenantId(id,tenantId,password,status);
	}
	
	/*
	 * 在微软新增客户信息
	 * @see com.office.service.CustomerService#saveCustomer(com.office.entity.Customer, java.lang.String)
	 */
	public Customer saveCustomer(Customer customer,String imagePath){
		
		JSONObject companyJson = new JSONObject();
		String attr = " {'objectType': 'CustomerCompanyProfile'}";
		companyJson.put("tenantId", null);
		companyJson.put("domain", customer.getDomain()+".partner.onmschina.cn");
		companyJson.put("companyName", customer.getCompanyName());
		companyJson.put("attributes", JSONObject.fromObject(attr));
		JSONObject addressJson = new JSONObject();
		addressJson.put("country", "CN");
		addressJson.put("state", customer.getAbbr());
		addressJson.put("city", customer.getCityName());
		addressJson.put("region", customer.getRegionName());
		addressJson.put("addressLine1", customer.getAddress());
		addressJson.put("postalCode", customer.getPostalCode());
		addressJson.put("firstName", customer.getFirstName());
		addressJson.put("lastName", customer.getLastName());
		addressJson.put("phoneNumber", customer.getPhoneNumber());
		JSONObject billingJson = new JSONObject();
		attr = "{'objectType': 'CustomerBillingProfile'}";
		billingJson.put("attributes", JSONObject.fromObject(attr));
		billingJson.put("defaultAddress", addressJson);
		billingJson.put("id", null);
		billingJson.put("firstName", customer.getFirstName());
		billingJson.put("lastName", customer.getLastName());
		billingJson.put("email", customer.getEmail());
		billingJson.put("culture", "zh-cn");
		billingJson.put("language", "zh-CHS");
		billingJson.put("companyName", customer.getCompanyName());
		JSONObject customerJson = new JSONObject();
		customerJson.put("companyProfile", companyJson);
		customerJson.put("billingProfile", billingJson);
		String access_token = RestfulUtil.getAccessToken();
		String targetURL = "https://partner.partnercenterapi.microsoftonline.cn/v1/customers"; 
		String method = "POST";
		Map<String, String> paramHeader = new HashMap<String, String>();
		paramHeader.put("Accept", "application/json");
		paramHeader.put("Content-Type", "application/json");
		paramHeader.put("Authorization",  "Bearer "+access_token);
		String paramBody = customerJson.toString();       
		JSONObject resultJson = RestfulUtil.getRestfulData(targetURL,method,paramHeader,paramBody);

		if(resultJson.get("responseCode").toString().startsWith("2")){
			//2018-04-02 该接口返回值前有一个特殊符号，需要截取掉再转换成json
			String result = resultJson.get("result").toString();
			if(result.indexOf("{")>=0){
				JSONObject reCustomerJson = JSONObject.fromObject(result.substring(result.indexOf("{")));
				String tenantId = reCustomerJson.get("id")==null?"":reCustomerJson.get("id").toString();
				customer.setTenantId(tenantId);
				JSONObject json = (JSONObject)reCustomerJson.get("userCredentials");
				String password = json.get("password")==null?"":AesUtil.aesEncrypt(json.get("password").toString());
				customer.setPassword(password);
				customerMapper.updateTenantId(customer.getId(), tenantId,password,"2");
				// 发送邮件
				Mail mail = new Mail();
				Multipart multipart = mail.setContect(customer, imagePath, "customer");
				mail.sendMail(customer.getEmail(),"欢迎使用office365", multipart);
			}
		}
		return customer;
	}
	
	/*
	 * 新增客户经销商关系
	 * @see com.office.service.CustomerService#insertRelationship(com.office.entity.Relationship)
	 */
	public void insertRelationship(Relationship relationship) {
		customerMapper.insertRelationship(relationship);
	}
	
	/*
	 * 删除客户
	 * @see com.office.service.CustomerService#deleteCustomer(com.office.entity.Customer)
	 */
	public String deleteCustomer(Customer customer) {
		String message = "success";
		if(("0".equals(customer.getStatus())||"3".equals(customer.getStatus()))&&("".equals(customer.getTenantId())||customer.getTenantId()==null)){
			int count = customerMapper.getOrdersCount(customer.getId());
			if(count>=1){
				message="该客户已下订单，无法删除！";
			}else{
				customerMapper.deleteCustomer(customer.getId());
				customerMapper.deleteRelationship(customer.getId());
			}
		}else{
			message="该客户无法删除！";
		}
		return message;
	}
}
