package com.office.mapper;

import java.util.List;
import com.office.entity.OfferPrice;

public interface OfferPriceMapper  {

	public List<OfferPrice> listPageOfferPrice(OfferPrice offerPrice);
	
	public OfferPrice getOfferPriceById(String id);
	
	public OfferPrice getPriceByOfferId(String offerId);
	
	public void insertOfferPrice(OfferPrice offerPrice);
	
	public void updateOfferPrice(OfferPrice offerPrice);
	
	public void deleteOfferPrice(String offerId);
}
