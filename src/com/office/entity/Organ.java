package com.office.entity;

import java.util.Date;
import java.util.List;

public class Organ {
	private String orgId;//组织ID
	private String orgName;//组织名称
	private String parentId;//父级
	private String sort;//排序
	private Integer level;//级别
	private String remark;//备注
	private Integer valid;//是否有效
	public String phonetic;//英文对照
	public String abbr;//英文简称
	private Date start;//开始时间
	private Date end;//结束时间
	private List<Organ> subOrgan;//子集
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public String getPhonetic() {
		return phonetic;
	}
	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public List<Organ> getSubOrgan() {
		return subOrgan;
	}
	public void setSubOrgan(List<Organ> subOrgan) {
		this.subOrgan = subOrgan;
	}
	
}
