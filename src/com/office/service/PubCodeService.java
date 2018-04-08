package com.office.service;

import java.util.HashMap;
import java.util.List;
import com.office.entity.PubCode;

public interface PubCodeService {
	
	/*
	 * 根据类别查询公共编码
	 */
	public List<PubCode> listPubCodeByClass(String classId);
	
	/*
	 * 分页查询公共编码信息
	 */
	public List<PubCode> listPagePubCode(PubCode pubCode);
	
	/*
	 * 根据类别查询公共编码
	 */
	public HashMap<String,String> codeMapByClass(String classId);
	
	/*
	 * 查询公共编码信息
	 */
	public HashMap<String,String> allCodeMap(PubCode pubCode);
	
	/*
	 * 新增公共编码信息
	 */
	public void insertPubCode(PubCode pubCode);
}
