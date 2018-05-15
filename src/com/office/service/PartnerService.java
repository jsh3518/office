package com.office.service;

import com.office.entity.Partner;

public interface PartnerService {
	
	/*
	 * 查询partner
	 */
	public Partner getPartner(Partner partner);

	/*
	 * 新增partner
	 */
	public void insertPartner(Partner partner);
	
	/*
	 * 更新partner
	 */
	public void updatePartner(Partner partner);
}
