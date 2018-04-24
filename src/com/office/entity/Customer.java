package com.office.entity;

import java.util.Date;

public class Customer {
	
	private Integer id;//用户id
	private String companyName;//公司名称
	private String country;//国家,默认中国(CN)
	private String province;//省份-id
	private String city;//市-id
	private String region;//区-id
	private String provincialName;//省份-名称
	private String cityName;//市-名称
	private String regionName;//区-名称
	private String abbr;//省份-简称
	private String address;//详细地址
	private String postalCode;//邮政编码
	private String email;//邮箱
	private String firstName;//名字
	private String lastName;//姓
	private String phoneNumber;//电话号码
	private String domain;//域名
	private String domain1;//域名1
	private String domain2;//域名2
	private String status;//状态
	private String tenantId;//O365客户Id
	private Date createTime;//创建时间
	private String createUser;//创建者
	private String password;//临时密码
	
	private Page page;//分页
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getProvincialName() {
		return provincialName;
	}
	public void setProvincialName(String provincialName) {
		this.provincialName = provincialName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDomain1() {
		return domain1;
	}
	public void setDomain1(String domain1) {
		this.domain1 = domain1;
	}
	public String getDomain2() {
		return domain2;
	}
	public void setDomain2(String domain2) {
		this.domain2 = domain2;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Page getPage() {
		if(page==null)
			page = new Page();
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}

}
