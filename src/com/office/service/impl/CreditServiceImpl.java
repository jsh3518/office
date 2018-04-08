package com.office.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.entity.Credit;
import com.office.mapper.CreditMapper;
import com.office.service.CreditService;

@Service("CreditService")
public class CreditServiceImpl implements CreditService {
	@Resource(name="creditMapper")
	@Autowired
	private CreditMapper creditMapper;

	/*
	 * 新增代理商信用
	 * @see com.office.service.CreditService#insertCredit(com.office.entity.Credit)
	 */
	public void insertCredit(Credit credit) {
		creditMapper.insertCredit(credit);
	}
	
	/*
	 * 查询代理商信用列表
	 * @see com.office.service.CreditService#listCredit(com.office.entity.Credit)
	 */
	public List<Credit> listPageCredit(Credit credit){
		return creditMapper.listPageCredit(credit);
	}
	
	/*
	 * 查询代理商信用
	 * @see com.office.service.CreditService#queryCredit(int)
	 */
	public Credit queryCredit(int agentId){
		return creditMapper.queryCredit(agentId);
	}
	
	/*
	 * 更新代理商信用信息
	 * @see com.office.service.CreditService#updateCredit(com.office.entity.Credit)
	 */
	public void updateCredit(Credit credit){
		creditMapper.updateCredit(credit);
	}

}
