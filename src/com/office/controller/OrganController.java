package com.office.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.office.entity.Organ;
import com.office.service.OrganService;

@Controller
@RequestMapping(value="/organ")
public class OrganController {

	@Autowired
	private OrganService organService;
	
	/**
	 * 显示用户列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/getOrgans")
	public void getOrgans(@RequestParam int level,@RequestParam String parentId,HttpServletResponse response){
		List<Organ> listOrgan = organService.listOrganByParent(level, parentId);
		JSONArray arr = JSONArray.fromObject(listOrgan);
		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			String json = arr.toString();
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
