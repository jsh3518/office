package com.office.mapper;

import java.util.List;

import com.office.entity.Customer;
import com.office.entity.Offer;

public interface OfferMapper  {

	List<Offer> listOfferByLevel(int level);
	
	List<Offer> listOfferByParent(String parent);
	
	void insertCustomer(Customer customer);
}
