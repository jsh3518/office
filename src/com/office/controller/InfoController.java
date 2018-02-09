package com.office.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.office.entity.Info;
import com.office.entity.Page;
import com.office.service.InfoService;

@Controller
@RequestMapping(value="/info")
public class InfoController {
	
	@Autowired
	private InfoService infoService;
	
	@RequestMapping
	public String info(Model model,Page page){
		List<Info> infoList = infoService.listPageInfo(page);
		model.addAttribute("infoList", infoList);
		model.addAttribute("page", page);
		return "info";
	}
	
}
