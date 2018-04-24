package com.office.service;

import java.util.List;

import com.office.entity.OfferPrice;

public interface OfferPriceService {
	
	/*
	 * 查询产品价格列表
	 */
	public List<OfferPrice> listPageOfferPrice(OfferPrice offerPrice);
	
	/*
	 * 查询产品id查询价格
	 */
	public OfferPrice getOfferPriceById(String id);
	
	/*
	 * 保存产品价格
	 */
	public void saveOfferPrice(OfferPrice offerPrice);
}
