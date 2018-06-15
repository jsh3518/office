package com.office.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.Offer;
import com.office.service.OfferService;
import com.office.util.RestfulUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/offer")
public class OfferController {

	@Autowired
	private OfferService offerService;
	
	/**
	 * 显示产品信息列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/rootOffers")
	public ModelAndView rootOffers(HttpServletResponse response){
		List<Offer> offerList = offerService.listOfferByLevel(1, null);
		ModelAndView mv = new ModelAndView();
		mv.addObject("offerList", offerList);
		mv.setViewName("offer/offers");
		return mv;
	}
	
	/**
	 * 
	 * @param req - parent
	 * @return
	 */
	@RequestMapping(value="/getOffer")
	public ModelAndView getOffer(HttpServletRequest req,Offer offer){
		List<Offer> listOffer = offerService.listPageOffer(offer);
		ModelAndView mv = new ModelAndView();
		mv.addObject("listOffer", listOffer);
		mv.addObject("offer", offer);
		mv.setViewName("offer/offer_list");
		return mv;
	}
	
	/**
	 * 产品明细页面
	 * @param offerId
	 * @return
	 */
	@RequestMapping(value="/detail")
	public ModelAndView detail(HttpServletRequest req){
		ModelAndView mv = new ModelAndView();
		String offerId = req.getParameter("offerId");
		Offer offer = offerService.getOffer(offerId);
		if(offer==null){
			offer = new Offer();
			String parent = req.getParameter("parent");
			if("Trial".equals(parent)){
				offer.setIsTrial("1");
			}else{
				offer.setIsTrial("0");
			}
			offer.setLevel("2");
			offer.setParent(req.getParameter("parent"));
		}
		mv.addObject("offer", offer);
		mv.addObject("method","detail");
		mv.setViewName("offer/offer_detail");
		return mv;
	}
	
	/**
	 * 删除选中产品
	 * @param offerId
	 * @param out
	 */
	@RequestMapping(value="/deleteOffer")
	public void deleteOffer(@RequestParam String offerId,PrintWriter out){
		offerService.deleteOffer(offerId);
		out.write("success");
		out.close();
	}
	
	/**
	 * 保存发票信息
	 * @param offer
	 * @return
	 */
	@RequestMapping(value="/saveOffer",method=RequestMethod.POST)
	public ModelAndView saveOffer(Offer offer){
		ModelAndView mv = new ModelAndView();
		offerService.saveOffer(offer);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**
	 * 判断offerId是否正确
	 * @param offerId
	 * @param response
	 */
	@RequestMapping(value="/checkOfferId")
	public void checkOfferId(String offerId,HttpServletResponse response){
		String msg = "";
		if(offerService.getCountById(offerId)>0){
			msg = "产品ID重复，请重新输入！";
		}else{
			String access_token = RestfulUtil.getAccessToken();
			String targetURL = RestfulUtil.getBaseUrl()+"/offers/"+offerId+"?country=CN"; 
			String method = "GET";
			Map<String, String> paramHeader = new HashMap<String, String>();
			paramHeader.put("Accept", "application/json");
			paramHeader.put("Content-Type", "application/json");
			paramHeader.put("Authorization",  "Bearer "+access_token);
			JSONObject resultJson = RestfulUtil.getRestfulData(targetURL,method,paramHeader,null);
			String responseCode = resultJson.get("responseCode")==null?"":resultJson.get("responseCode").toString();
			if(!responseCode.startsWith("2")){
				msg = "PAC系统中未找到对应的产品，请重新输入！";
			}
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
