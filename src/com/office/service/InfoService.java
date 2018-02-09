package com.office.service;

import java.util.List;

import com.office.entity.Info;
import com.office.entity.Page;

public interface InfoService {
	List<Info> listPageInfo(Page page);
}
