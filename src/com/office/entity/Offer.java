package com.office.entity;

import java.util.List;

public class Offer {
	
	private String offerId;//office产品id
	private String offerName;//office产品名称
	private String parent;//父级
	private String level;//层级
	private String sort;//排序
	private String isTrial;//是否试用产品
	private String status;//状态
	
	private List<Offer> subOffer;//下级产品集合
	
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
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getIsTrial() {
		return isTrial;
	}
	public void setIsTrial(String isTrial) {
		this.isTrial = isTrial;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Offer> getSubOffer() {
		return subOffer;
	}
	public void setSubOffer(List<Offer> subOffer) {
		this.subOffer = subOffer;
	}
	
}
