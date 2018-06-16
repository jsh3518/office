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
	List<Offer> listOfferByParent(String  parent);
	
	/*
	 * 查询产品列表
	 */
	List<Offer> listPageOffer(Offer  offer);
	
	/*
	 * 根据产品id查询产品信息
	 */
	Offer getOffer(String OfferId);
	
	//根据offerId删除产品
	 void deleteOffer(String offerId);
	 
	 //更新产品信息
	 void saveOffer(Offer offer);
	 
	 //更新产品信息
	 int getCountById(String offerId);
	 
	 //查询订单明细个数
	 int getOrdersCount(String offerId);
}
