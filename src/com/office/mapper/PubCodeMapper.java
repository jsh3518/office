package com.office.mapper;

import java.util.List;
import com.office.entity.PubCode;

public interface PubCodeMapper  {

	public List<PubCode> listPubCodeByClass(String classId);

	public List<PubCode> listPagePubCode(PubCode pubCode);
	
	public PubCode getPubCodeById(String id);
	
	public PubCode getPubCode(PubCode pubCode);
	
	public void insertPubCode(PubCode pubCode);

	public void updatePubCode(PubCode pubCode);
	
	public void deletePubCode(String id);
	
	public int getCount(PubCode pubCode);
}
