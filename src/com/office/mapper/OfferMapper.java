package com.office.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.office.entity.Offer;

public interface OfferMapper  {

	List<Offer> listOfferByLevel(@Param("level")int level,@Param("isTrial")String isTrial);
	
	List<Offer> listOfferByParent(String parent);
	
	Offer getOffer(String  offerId);
	
	void deleteOffer(String offerId);
	
	void updateOffer(Offer offer);
	
	void insertOffer(Offer offer);
	
	List<Offer> listPageOffer(Offer offer);
	
	int getCountById(String offerId);
}
