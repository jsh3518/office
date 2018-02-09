package com.office.mapper;

import java.util.List;

import com.office.entity.Info;
import com.office.entity.Page;

public interface InfoMapper {
	List<Info> listPageInfo(Page page);
}
