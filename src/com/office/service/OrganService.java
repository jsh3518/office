package com.office.service;

import java.util.List;

import com.office.entity.Organ;

public interface OrganService {
	
	//根据组织Id获取组织信息
	Organ getOrganById(String organId);
	//新增组织
	void insertOrgan(Organ organ);
	//更新组织
	void updateOrgan(Organ organ);
	//根据组织Id删除组织
	void deleteOrgan(String organId);
	//根据级别查询组织
	List<Organ> listOrganByLevel(int level);
	//根据父级Id查询组织
	List<Organ> listOrganByParent(int level,String parentId);
	//查询组织信息
	List<Organ> listOrgan(Organ organ);
	//查询所有组织信息
	List<Organ> listAllOrgan();
}
