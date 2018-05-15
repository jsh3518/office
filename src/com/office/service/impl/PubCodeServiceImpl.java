package com.office.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.entity.PubCode;
import com.office.mapper.PubCodeMapper;
import com.office.service.PubCodeService;

@Service("PubCodeService")
public class PubCodeServiceImpl implements PubCodeService {
	
	@Resource(name="pubCodeMapper")
	@Autowired
	private PubCodeMapper pubCodeMapper;

	public PubCodeMapper getPubCodeMapper() {
		return pubCodeMapper;
	}

	public void setPubCodeMapper(PubCodeMapper pubCodeMapper) {
		this.pubCodeMapper = pubCodeMapper;
	}

	/**
	 * 根据类别查询公共编码
	 * @param classId
	 * @return
	 */
	public List<PubCode> listPubCodeByClass(String classId){
		return pubCodeMapper.listPubCodeByClass(classId);
	}
	
	/**
	 * 分页查询公共编码
	 * @param pubCode
	 * @return
	 */
	public List<PubCode> listPagePubCode(PubCode pubCode) {
		return pubCodeMapper.listPagePubCode(pubCode);
	}

	
	/**
	 * 根据id查询公共编码
	 * @param id
	 * @return
	 */
	public PubCode getPubCodeById(String id) {
		return pubCodeMapper.getPubCodeById(id);
	}
	
	/**
	 * 查询公共编码
	 * @param pubCode
	 * @return
	 */
	public PubCode getPubCode(PubCode pubCode) {
		return pubCodeMapper.getPubCode(pubCode);
	}
	
	/**
	 * 新增公共编码
	 * @param pubCode
	 */
	public void insertPubCode(PubCode pubCode) {
		pubCodeMapper.insertPubCode(pubCode);
	}

	/**
	 * 更新公共编码
	 * @param pubCode
	 */
	public void updatePubCode(PubCode pubCode) {
		pubCodeMapper.updatePubCode(pubCode);
	}
	
	/**
	 * 删除公共编码
	 * @param id
	 */
	public void deletePubCode(String id) {
		pubCodeMapper.deletePubCode(id);
	}
	
	
	public int getCount(PubCode pubCode){
		return pubCodeMapper.getCount(pubCode);
	}
	
	/**
	 * 根据类别查询公共编码
	 * @param classId
	 * @return
	 */
	public HashMap<String, String> codeMapByClass(String classId) {
		List<PubCode> pubCodeList = pubCodeMapper.listPubCodeByClass(classId);
		HashMap<String, String> codeMap = new HashMap<String, String>();
		for(PubCode pubCode : pubCodeList) {
			if(pubCode.getCode()!=null&&!"".equals(pubCode.getCode())){
				codeMap.put(pubCode.getCode(), pubCode.getName());
			}
		}
		return codeMap;
	}
	
	/**
	 * 查询公共编码
	 * @param pubCode
	 * @return
	 */
	public HashMap<String, String> allCodeMap(PubCode pubCode) {
		List<PubCode> pubCodeList = pubCodeMapper.listPagePubCode(pubCode);
		HashMap<String, String> codeMap = new HashMap<String, String>();
		for(PubCode tmpPubCode : pubCodeList) {
			if(tmpPubCode.getCode()!=null&&!"".equals(tmpPubCode.getCode())){
				codeMap.put(tmpPubCode.getCode(), tmpPubCode.getName());
			}
		}
		return codeMap;
	}

}
