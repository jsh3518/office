package com.office.service;

import java.util.List;
import com.office.entity.Credit;

public interface CreditService {
	
	/*
	 * 新增订单
	 */
	void insertCredit(Credit credit);
	
	/*
	 * 查询代理商信用列表
	 */
	public List<Credit> listPageCredit(Credit credit);

	/*
	 * 查询代理商信用
	 */
	public Credit queryCredit(int agentId);
	
	/*
	 * 更新代理商信用信息
	 */
	public void updateCredit(Credit credit);
}
