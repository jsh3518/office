package com.office.entity;

import java.util.List;

public class Offer {
	
	private String offerId;//office产品id
	private String offerName;//office产品名称
	private String parent;//父级
	private String level;//层级
	private String mininum;//最小数量
	private String maxinum;//最大数量
	private String sort;//排序
	private String isTrial;//是否试用产品
	private String valid;//是否有效
	
	private List<Offer> subOffer;//下级产品集合
	private Page page;//分页
	
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
	public String getMininum() {
		return mininum;
	}
	public void setMininum(String mininum) {
		this.mininum = mininum;
	}
	public String getMaxinum() {
		return maxinum;
	}
	public void setMaxinum(String maxinum) {
		this.maxinum = maxinum;
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
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public List<Offer> getSubOffer() {
		return subOffer;
	}
	public void setSubOffer(List<Offer> subOffer) {
		this.subOffer = subOffer;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
}
