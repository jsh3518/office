package com.office.entity;

import java.math.BigDecimal;

public class OfferPrice {
	
	private Integer id;
	private String offerId;//office产品id
	private String offerName;//office产品名称
	private BigDecimal priceMonth;//月单价
	private BigDecimal priceYear;//年单价
	private String unit;//单位
	private String unitName;//单位
	private String valid;//是否有效
	private Page page;//分页
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public BigDecimal getPriceMonth() {
		return priceMonth;
	}
	public void setPriceMonth(BigDecimal priceMonth) {
		this.priceMonth = priceMonth;
	}
	public BigDecimal getPriceYear() {
		return priceYear;
	}
	public void setPriceYear(BigDecimal priceYear) {
		this.priceYear = priceYear;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}

	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
}
