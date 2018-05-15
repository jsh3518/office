package com.office.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.entity.Partner;
import com.office.mapper.PartnerMapper;
import com.office.service.PartnerService;

@Service("PartnerService")
public class PartnerServiceImpl implements PartnerService {
	
	@Resource(name="partnerMapper")
	@Autowired
	private PartnerMapper partnerMapper;

	public PartnerMapper getPartnerMapper() {
		return partnerMapper;
	}

	public void setPartnerMapper(PartnerMapper partnerMapper) {
		this.partnerMapper = partnerMapper;
	}

	/**
	 * 查询partner
	 * @param partner
	 * @return
	 */
	public Partner getPartner(Partner partner){
		return partnerMapper.getPartner(partner);
	}

	/**
	 * 新增partner
	 * @param partner
	 */
	public void insertPartner(Partner partner) {
		partnerMapper.insertPartner(partner);
	}

	/**
	 * 更新partner
	 * @param partner
	 */
	public void updatePartner(Partner partner) {
		partnerMapper.updatePartner(partner);
	}
}
