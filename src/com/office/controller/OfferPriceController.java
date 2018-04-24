package com.office.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.OfferPrice;
import com.office.entity.PubCode;
import com.office.service.OfferPriceService;
import com.office.service.PubCodeService;

@Controller
@RequestMapping(value="/offerPrice")
public class OfferPriceController {

	@Autowired
	private OfferPriceService offerPriceService;

	@Autowired
	private PubCodeService pubCodeService;
	/**
	 * 显示产品信息列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/getOfferPrice")
	public String getOfferPrice(Model model,OfferPrice offerPrice){
		List<OfferPrice> listOfferPrice = offerPriceService.listPageOfferPrice(offerPrice);
		model.addAttribute("listOfferPrice", listOfferPrice);
		return "offer/price_list";
	}
	
	
	/**
	 * 请求编辑用户页面
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/detail")
	public ModelAndView detail(String id,String offerId,String offerName1,String method){
		ModelAndView mv = new ModelAndView();
		OfferPrice offerPrice = new OfferPrice();
		if("".equals(id)||id==null){
			offerPrice.setOfferId(offerId);
			try {
				offerPrice.setOfferName(new String(offerName1.getBytes("iso8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else{
			offerPrice = offerPriceService.getOfferPriceById(id);
		}
		List<PubCode> unitList = pubCodeService.listPubCodeByClass("AMOUNTUNIT");
		mv.addObject("offerPrice", offerPrice);
		mv.addObject("unitList", unitList);
		mv.addObject("method", method);
		mv.setViewName("offer/offerprice");
		return mv;
	}
	
	/**
	 * 更新产品价格信息
	 * @param roleId
	 * @param menuIds
	 * @param out
	 */
	@RequestMapping(value="/saveOfferPrice")
	public String saveOfferPrice(OfferPrice offerPrice,Model model){

		offerPriceService.saveOfferPrice(offerPrice);
		model.addAttribute("msg", "success");
		return "save_result";
	}
	
}
