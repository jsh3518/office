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

import com.office.entity.Offer;
import com.office.service.OfferService;

@Controller
@RequestMapping(value="/offer")
public class OfferController {

	@Autowired
	private OfferService offerService;
	
	/**
	 * 显示用户列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/getOffers")
	public void getOrgans(@RequestParam String parentId,HttpServletResponse response){
		List<Offer> listOffer = offerService.listOfferByParent(parentId);
		JSONArray arr = JSONArray.fromObject(listOffer);
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
