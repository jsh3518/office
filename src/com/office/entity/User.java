package com.office.entity;

import java.util.Date;

public class User {
	private Integer userId;//用户ID
	private String loginname;//登录名
	private String username;//用户名
	private String password;//用户密码
	private String rights;//权限
	private Integer status;//状态
	private Integer roleId;//角色ID
	private String contact;//联系人
	private String phone;//联系方式
	private String email;//邮箱
	private String provincialId;//地址--省(ID)
	private String cityId;//地址--市(ID)
	private String countyId;//地址--县/区(ID)
	private String address;//地址--详细地址
	private String post;//邮政编码
	private String tax;//税号
	private String file;//附件
	private Integer type;//类型
	private Date lastLogin;//最后登录时间
	private Date lastLoginStart;
	private Date lastLoginEnd;
	private Role role;//角色
	private Page page;
	private String mpnId;//MPNID
	
	private Organ province;//地址--省
	private Organ city;//地址--市
	private Organ county;//地址--县/区
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Date getLastLoginStart() {
		return lastLoginStart;
	}
	public void setLastLoginStart(Date lastLoginStart) {
		this.lastLoginStart = lastLoginStart;
	}
	public Date getLastLoginEnd() {
		return lastLoginEnd;
	}
	public void setLastLoginEnd(Date lastLoginEnd) {
		this.lastLoginEnd = lastLoginEnd;
	}
	public Page getPage() {
		if(page==null)
			page = new Page();
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}

	public String getMpnId() {
		return mpnId;
	}
	public void setMpnId(String mpnId) {
		this.mpnId = mpnId;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Organ getProvince() {
		return province;
	}
	public void setProvince(Organ province) {
		this.province = province;
	}
	public Organ getCity() {
		return city;
	}
	public void setCity(Organ city) {
		this.city = city;
	}
	public Organ getCounty() {
		return county;
	}
	public void setCounty(Organ county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getProvincialId() {
		return provincialId;
	}
	public void setProvincialId(String provincialId) {
		this.provincialId = provincialId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCountyId() {
		return countyId;
	}
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}
	
}
