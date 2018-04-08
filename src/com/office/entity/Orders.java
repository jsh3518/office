package com.office.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Orders {
	
	private Integer id;//订单id
	private String ordersNo;//订单编号
	private Integer customerId;//用户Id
	private String billingCycle;//收费周期
	private Date createTime;//创建时间
	private Date effectTime;//生效时间
	private BigDecimal sum;//金额
	private String status;//状态
	private String mpnId;//经销商Id
	private String reseller;//经销商名称
	private String createUser;//创建者
	private String payment;//付款方式
	private String file;//附件
	private String orderId;//O365订单Id
	
	private Customer customer;//客户信息
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}
	public String getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	public String getMpnId() {
		return mpnId;
	}
	public void setMpnId(String mpnId) {
		this.mpnId = mpnId;
	}

	public String getReseller() {
		return reseller;
	}
	public void setReseller(String reseller) {
		this.reseller = reseller;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
