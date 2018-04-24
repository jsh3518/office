package com.office.entity;

import java.util.Date;

public class Subscription {
	
	private Integer id;//订阅id
	private Integer customerId;//客户Id
	private Integer detailId;//订单明细Id
	private String offerId;//Office产品Id
	private String offerName;//Office产品名称
	private String quantity;//坐席数量
	private String billingCycle;//收费周期
	private String subscriptionId;//O365订阅Id
	private Date effectTime;//生效时间
	private Integer renew;//续订时长（单位：月）
	private String mpnId;//经销商Id
	private String reseller;//经销商名称
	private String createUser;//创建者
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public String getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public Date getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	public Integer getRenew() {
		return renew;
	}
	public void setRenew(Integer renew) {
		this.renew = renew;
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

}
