package com.office.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.Invoice;
import com.office.entity.User;
import com.office.service.InvoiceService;
import com.office.util.Const;

@Controller
@RequestMapping(value="/invoice")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;
	
	@RequestMapping
	public ModelAndView list(Invoice invoice){
		List<Invoice> invoiceList =  invoiceService.listPageInvoice(invoice);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("invoice/invoice");
		mv.addObject("invoiceList", invoiceList);
		return mv;
	}
	
	/**
	 * 请求新增用户页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/detail")
	public ModelAndView detail(Invoice invoice,HttpSession session){
		User user= (User)session.getAttribute(Const.SESSION_USER);
		invoice.setUserId(user.getLoginname());
		invoice = invoiceService.getInvoice(invoice);
		ModelAndView mv = new ModelAndView();
		mv.addObject("invoice", invoice);
		mv.addObject("user", user);
		mv.setViewName("invoice/invoice_detail");
		return mv;
	}
	
	/**
	 * 删除发票信息
	 * @param userId
	 * @param out
	 */
	@RequestMapping(value="/delete")
	public void deleteInvoice(@RequestParam int id,PrintWriter out){
		invoiceService.delete(id);
		out.write("success");
		out.close();
	}
	
	/**
	 * 保存发票信息
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public ModelAndView saveInvoice(Invoice invoice,HttpSession session){
		ModelAndView mv = new ModelAndView();
		if(invoice.getValid()==null||"".equals(invoice.getValid())){
			invoice.setValid("1");
		}
		if(invoice.getId() == null) {
			invoice.setUserId((String)session.getAttribute(Const.SESSION_USER_ID));
			invoiceService.insert(invoice);
		}else {
			invoiceService.updateInfo(invoice);
		}
		mv.addObject("invoice",invoice);
		mv.addObject("message", "success");
		mv.setViewName("invoice/invoice_detail");
		return mv;
	}
	
}
