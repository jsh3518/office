package com.office.service.impl;

import java.util.List;

import com.office.entity.Offer;
import com.office.mapper.OfferMapper;
import com.office.service.OfferService;

public class OfferServiceImpl implements OfferService {


	private OfferMapper offerMapper;
	
	public OfferMapper getOfferMapper() {
		return offerMapper;
	}

	public void setOfferMapper(OfferMapper offerMapper) {
		this.offerMapper = offerMapper;
	}

	/**
	 * 根据级别查询产品列表
	 * @param parent
	 * @return
	 */
	public List<Offer> listOfferByLevel(int level,String isTrial) {
		return offerMapper.listOfferByLevel(level,isTrial);
	}
	
	/**
	 * 根据父级查询产品列表
	 * @param parent
	 * @return
	 */
	public List<Offer> listOfferByParent(String parent) {
		
		return offerMapper.listOfferByParent(parent);
	}
	
	/**
	 * 查询产品列表
	 * @param offer
	 * @return
	 */
	public List<Offer> listPageOffer(Offer offer) {
		
		return offerMapper.listPageOffer(offer);
	}
	/**
	 * 根据Id查询产品
	 * @param offerId
	 * @return
	 * 
	 */
	public Offer getOffer(String OfferId){
		return offerMapper.getOffer(OfferId);
	}

	/*
	 * 删除产品信息
	 * @see com.office.service.OfferService#deleteOffer(java.lang.String)
	 */
	public void deleteOffer(String offerId) {
		
		offerMapper.deleteOffer(offerId);
		
	}

	/*
	 * 保存产品信息
	 * @see com.office.service.OfferService#saveOffer(com.office.entity.Offer)
	 */
	public void saveOffer(Offer offer) {
		int count = offerMapper.getCountById(offer.getOfferId());
		if(count<=0){
			offerMapper.insertOffer(offer);
		}else{
			offerMapper.updateOffer(offer);
		}
	}
	
	/*
	 * 查询产品个数
	 * @see com.office.service.OfferService#getCountById(java.lang.String)
	 */
	public int getCountById(String offerId) {
		 return  offerMapper.getCountById(offerId);
	}
	
}
