package com.office.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.PubCode;
import com.office.service.PubCodeService;

@Controller
@RequestMapping(value = "/pubCode")
public class PubCodeController {

	@Autowired
	private PubCodeService pubCodeService;

	/**
	 * 显示代理商信用列表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(String classId) {
		PubCode pubCode = new PubCode();
		pubCode.setClassId(classId);
		List<PubCode> pubCodeList = pubCodeService.listPagePubCode(pubCode);

		ModelAndView mv = new ModelAndView();
		mv.addObject("pubCodeList", pubCodeList);
		mv.addObject("classId", classId);
		mv.setViewName("basic/pubcode");
		return mv;
	}

	/**
	 * 
	 * @param agentId
	 * @return
	 */
	@RequestMapping(value = "/queryPubCode")
	public ModelAndView queryPubCode(String id,String classId) {
		PubCode pubCode = new PubCode();
		if(id==null||"".endsWith("id")){
			pubCode.setClassId(classId);
		}else{
			pubCode = pubCodeService.getPubCodeById(id);
		}

		ModelAndView mv = new ModelAndView();
		mv.addObject("pubCode", pubCode);
		mv.setViewName("basic/detail");
		return mv;
	}

	/**
	 * 修改代理商信用信息
	 * 
	 * @param pubCode
	 * @return
	 */
	@RequestMapping(value = "/editPubCode")
	public ModelAndView editPubCode(PubCode pubCode) {
		if (pubCode.getId() == null||"".equals(pubCode.getId())) {
			pubCodeService.insertPubCode(pubCode);
		} else {
			pubCodeService.updatePubCode(pubCode);
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 * @param id
	 * @param out
	 */
	@RequestMapping(value="/delete")
	public void delete(@RequestParam String id,PrintWriter out){
		pubCodeService.deletePubCode(id);
		out.write("删除成功！");
		out.flush();
		out.close();
	}
	
	/**
	 * 判断验证码是否正确
	 * @param code
	 * @param response
	 */
	@RequestMapping(value="/checkCode")
	public void checkCode(String code,String id,String classId,HttpServletResponse response){
		PubCode pubCode = new PubCode();
		pubCode.setClassId(classId);
		pubCode.setCode(code);
		pubCode.setId(id);
		String msg = "";
		if(pubCodeService.getCount(pubCode)>0){
			msg = "编码重复，请重新输入！";
		}
		
		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			out.write(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
