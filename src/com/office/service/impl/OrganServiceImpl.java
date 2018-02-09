package com.office.service.impl;

import java.util.List;

import com.office.entity.Organ;
import com.office.mapper.OrganMapper;
import com.office.service.OrganService;

public class OrganServiceImpl implements OrganService {

	private OrganMapper organMapper;
	
	public Organ getOrganById(String organId) {
		return organMapper.getOrganById(organId);
	}

	public void insertOrgan(Organ organ) {
		organMapper.insertOrgan(organ);	
	}

	public void updateOrgan(Organ organ) {
		organMapper.updateOrgan(organ);
	}
	
	public OrganMapper getOrganMapper() {
		return organMapper;
	}

	public void setOrganMapper(OrganMapper organMapper) {
		this.organMapper = organMapper;
	}

	public void deleteOrgan(String organId){
		organMapper.deleteOrgan(organId);
	}

	public List<Organ> listAllOrgan() {
		return organMapper.listAllOrgan();
	}

	@Override
	public List<Organ> listOrganByLevel(int level) {
		return organMapper.listOrganByLevel(level);
	}

	@Override
	public List<Organ> listOrganByParent(int level, String parentId) {
		List<Organ> organList = organMapper.listOrganByParent(level, parentId);
		return organList;
	}

	@Override
	public List<Organ> listOrgan(Organ organ) {
		return organMapper.listOrgan(organ);
	}
	
}
