package com.office.service.impl;

import java.util.List;

import com.office.entity.Info;
import com.office.entity.Page;
import com.office.mapper.InfoMapper;
import com.office.service.InfoService;

public class InfoServiceImpl implements InfoService{

	private InfoMapper infoMapper;
	
	public List<Info> listPageInfo(Page page) {
		// TODO Auto-generated method stub
		return infoMapper.listPageInfo(page);
	}

	public InfoMapper getInfoMapper() {
		return infoMapper;
	}

	public void setInfoMapper(InfoMapper infoMapper) {
		this.infoMapper = infoMapper;
	}
}
