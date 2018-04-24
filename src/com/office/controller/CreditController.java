package com.office.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.Credit;
import com.office.entity.PubCode;
import com.office.entity.User;
import com.office.service.CreditService;
import com.office.service.PubCodeService;
import com.office.service.UserService;

@Controller
@RequestMapping(value="/credit")
public class CreditController {
	
	@Autowired
	private CreditService creditService;
	@Autowired
	private UserService userService;
	@Autowired
	private PubCodeService pubCodeService;

	private static Logger logger = Logger.getLogger(CreditController.class);//输出Log日志
	/**
	 * 显示代理商信用列表
	 * @param user
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(Credit credit){

		List<Credit> creditList = creditService.listPageCredit(credit);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("creditList", creditList);
		mv.setViewName("credit/credit_list");
		return mv;
	}
	
	/**
	 * 
	 * @param agentId
	 * @return
	 */
	@RequestMapping(value="/queryCredit")
	public ModelAndView queryCredit(int agentId){
		User agent = userService.getUserById(agentId);
		Credit credit = creditService.queryCredit(agentId);
		List<PubCode> creditList = pubCodeService.listPubCodeByClass("CREDITRATING");
		List<PubCode> unitList = pubCodeService.listPubCodeByClass("TIMEUNIT");
		ModelAndView mv = new ModelAndView();
		mv.addObject("credit", credit);
		mv.addObject("agent", agent);
		mv.addObject("creditList", creditList);
		mv.addObject("unitList", unitList);
		mv.setViewName("credit/credit_detail");
		return mv;
	}
	
	/**
	 * 修改代理商信用信息
	 * @param credit
	 * @return
	 */
	@RequestMapping(value="/editCredit")
	public ModelAndView editCredit(Credit credit){
		if(credit.getId()==null){
			creditService.insertCredit(credit);
		}else{
			creditService.updateCredit(credit);
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:../credit.html");
		return mv;
	}
}
