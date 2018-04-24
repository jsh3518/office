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

}
