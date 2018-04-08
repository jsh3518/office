package com.office.mapper;

import java.util.List;
import com.office.entity.Credit;

public interface CreditMapper  {
	
	public List<Credit> listPageCredit(Credit credit);
	
	public Credit queryCredit(int agetnId);
	
	void insertCredit(Credit Credit);
	
	public void updateCredit(Credit credit);
}
