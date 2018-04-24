package com.office.service;

import java.util.List;

import com.office.entity.Offer;

public interface OfferService {
	/*
	 * 根据父级查询产品列表
	 */
	List<Offer> listOfferByLevel(int level,String isTrial);
	
	/*
	 * 根据父级查询产品列表
	 */
	List<Offer> listOfferByParent(String parent);
}
