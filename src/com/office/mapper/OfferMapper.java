package com.office.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.office.entity.Offer;

public interface OfferMapper  {

	List<Offer> listOfferByLevel(@Param("level")int level,@Param("isTrial")String isTrial);
	
	List<Offer> listOfferByParent(String parent);
	
	List<Offer> listPageOffer(Offer offer);
	
	Offer getOffer(String OfferId);
}
