package com.office.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.Customer;
import com.office.entity.Organ;
import com.office.entity.Page;
import com.office.entity.Relationship;
import com.office.entity.User;
import com.office.service.CustomerService;
import com.office.service.OrganService;
import com.office.util.Const;
import com.office.util.RestfulUtil;
import com.office.util.Tools;

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
	 * 客户初始化页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/forAdd")
	public String forAdd(Model model,Customer customer){
		List<Organ> provinList = organService.listOrganByLevel(1);
		model.addAttribute("provinList", provinList);
		model.addAttribute("customer", customer);
		return "customer/customer_add";
	}

	/**
	 * 添加客户初始化页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addCustomer")
	public String addCustomer(Model model,Customer customer,HttpSession session){

		User user= (User)session.getAttribute(Const.SESSION_USER);
		customer.setCountry("CN");
		customer.setCreateTime(new Date());
		customer.setCreateUser(user==null?null:user.getLoginname());
		customer.setStatus("0");//客户新增
		customerService.insertCustomer(customer);
		Relationship relationship = new Relationship();
		relationship.setCustomerId(customer.getId()==null?"":customer.getId().toString());
		relationship.setUserId(user.getLoginname());
		relationship.setMpnId(user.getMpnId());
		relationship.setStartTime(new Date());
		relationship.setEndTime(Tools.str2Date("9999-12-31 23:59:59"));
		relationship.setValid("1");
		customerService.insertRelationship(relationship);
		return "redirect:../orders/forAdd.html?customerId="+customer.getId();
	}

	/**
	 * 客户信息更新
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/saveCustomer")
	public String saveCustomer(Model model,Customer customer,HttpSession session){

		customerService.updateCustomer(customer);
		model.addAttribute("msg", "success");
		return "save_result";
	}
	
	/**
	 * 保存用户信息
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="/detailCustomer")
	public ModelAndView detailCustomer(String customerId,String method){
		Customer customer = customerService.selectCustomerById(customerId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("customer", customer);

		List<Organ> provinList = organService.listOrganByLevel(1);
		mv.addObject("provinList", provinList);
		List<Organ> cityList = organService.listOrganByParent(2,customer==null?"":customer.getProvince());
		mv.addObject("cityList", cityList);
		List<Organ> regionList = organService.listOrganByParent(3,customer==null?"":customer.getCity());
		mv.addObject("regionList", regionList);
		if(method.equals("edit")){
			mv.setViewName("customer/customer_edit");
		}else{
			mv.setViewName("customer/customer_detail");
		}
		return mv;
	}
	
	/**
	 * 判断域名是否可用
	 * @param code
	 * @param response
	 */
	@RequestMapping(value="/domain")
	public void domain(String domain,String  customerId,HttpServletResponse response,HttpSession session){

		String access_token = RestfulUtil.getAccessToken();
		String targetURL = "https://partner.partnercenterapi.microsoftonline.cn/v1/domains/"+domain+".partner.onmschina.cn"; 
		String method = "HEAD";
		Map<String, String> paramHeader = new HashMap<String, String>();
		paramHeader.put("Accept", "application/json");
		paramHeader.put("Authorization",  "Bearer "+access_token);
		JSONObject resultJson = RestfulUtil.getRestfulData(targetURL,method,paramHeader,null);
		String responseCode = resultJson.get("responseCode")==null?"":resultJson.get("responseCode").toString();
		if("".equals(responseCode)||!responseCode.startsWith("2")){
			Customer customer = customerService.getCustomerDomain(domain,customerId);
			if(customer!=null){
				responseCode="900";
			}
		}
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
	
	@RequestMapping(value="/query")
	public String query(Model model,Page page,HttpSession session,HttpServletRequest req){
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String companyName = req.getParameter("companyName")==null?"":req.getParameter("companyName");
		String domain = req.getParameter("domain")==null?"":req.getParameter("domain");
		String flag = req.getParameter("flag")==null?"":req.getParameter("flag");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("companyName", companyName);
		map.put("domain", domain);
		if(user!=null&&user.getRoleId()!=2){
			map.put("userId", user.getLoginname());
			map.put("mpnId",user.getMpnId());
		}
		
		List<Customer> customerList = customerService.queryCustomer(map);
		model.addAttribute("customerList", customerList);
		model.addAttribute("page", page);
		model.addAttribute("companyName", companyName);
		model.addAttribute("domain", domain);
		model.addAttribute("flag", flag);
		if("index".equals(flag)){
			return "customer/customer_index";
		}else{
			return "customer/customer";
		}
	}
	
	/**
	 * 显示用户列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/getCustomer")
	public void getCustomer(@RequestParam String customerId,HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		Customer customer = customerService.selectCustomerById(customerId);
		jsonObject.put("customer", customer);

		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			String json = jsonObject.toString();
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
