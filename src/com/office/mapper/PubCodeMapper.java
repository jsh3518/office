package com.office.mapper;

import java.util.List;
import com.office.entity.PubCode;

public interface PubCodeMapper  {

	public List<PubCode> listPubCodeByClass(String classId);

	public List<PubCode> listPagePubCode(PubCode pubCode);
	
	public void insertPubCode(PubCode pubCode);

}
