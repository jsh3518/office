package com.office.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.Customer;
import com.office.entity.Organ;
import com.office.service.CustomerService;
import com.office.service.OrganService;
import com.office.util.Const;
import com.office.util.RestfulUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/customer")
public class CustomerController {
	@Autowired
	private OrganService organService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping
	public String info(Model model,Customer customer){
		List<Customer> customerList = customerService.listCustomer(customer);
		model.addAttribute("customerList", customerList);
		model.addAttribute("customer", customer);
		return "customer/customer";
	}
	
	/**
	 * 添加客户初始化页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/forAdd")
	public String forAdd(Model model){
		List<Organ> provinList = organService.listOrganByLevel(1);
		model.addAttribute("provinList", provinList);
		return "customer/customer_add";
	}

	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView addCustomer(Customer customer){
		customer.setCountry("CN");
		ModelAndView mv = new ModelAndView();
		customerService.insertCustomer(customer);
		mv.setViewName("default");
		return mv;
	}
	
	/**
	 * 判断域名是否可用
	 * @param code
	 * @param response
	 */
	@RequestMapping(value="/domain")
	public void checkDomain(String domain,HttpServletResponse response,HttpSession session){

		String access_token = (String)session.getAttribute(Const.ACCESS_TOKEN);
		if(access_token == null){//如果session中不包含access_token，则通过调用接口重新获取token
			JSONObject resultJson = RestfulUtil.getToken();
			access_token = resultJson.get("access_token")==null?"":resultJson.get("access_token").toString();
			session.setAttribute(Const.ACCESS_TOKEN, access_token);		
		}

		String targetURL = "https://partner.partnercenterapi.microsoftonline.cn/v1/domains/"+domain; 
		String method = "HEAD";
		Map<String, String> paramHeader = new HashMap<String, String>();
		paramHeader.put("Accept", "application/json");
		paramHeader.put("Authorization",  "Bearer "+access_token);
		JSONObject resultJson = RestfulUtil.getRestfulData(targetURL,method,paramHeader,null);
		String responseCode = resultJson.get("responseCode").toString();
		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			out.write(responseCode);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
