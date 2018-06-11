package com.office.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.Partner;
import com.office.service.PartnerService;
import com.office.util.AesUtil;
import com.office.util.RestfulUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/partner")
public class PartnerController {

	@Autowired
	private PartnerService partnerService;

	/**
	 * 显示Partner Center 账号信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping
	public ModelAndView getPartner() {
		Partner partner = new Partner();
		partner = partnerService.getPartner(partner);
		if(partner.getVersion()==null||"".equals(partner.getVersion())){
			partner.setVersion("1");
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("partner", partner);
		mv.setViewName("basic/partner");
		return mv;
	}

	/**
	 * 更新Partner Center 账号信息
	 * @param partner
	 * @return
	 */
	@RequestMapping(value = "/editPartner")
	public ModelAndView editPartner(Partner partner) {
		
		String message = "failed";
		//  验证密码
		String domain = partner.getUsername().substring(partner.getUsername().lastIndexOf("@")+1);
		String url = "https://login.chinacloudapi.cn/"+domain+"/oauth2/token?api-version="+partner.getVersion();
		String method = "POST";
		Map<String, String> paramHeader = new HashMap<String, String>();
		paramHeader.put("Content-Type", "application/x-www-form-urlencoded");
		paramHeader.put("Cache-Control", "no-cache");
		StringBuffer paramBody = new StringBuffer();
		paramBody.append("grant_type=password");
		paramBody.append("&scope=openid");
		paramBody.append("&resource=").append(partner.getResource());
		paramBody.append("&client_id=").append(partner.getAppid());
		paramBody.append("&username=").append(partner.getUsername());
		paramBody.append("&password=").append(partner.getPassword());
		JSONObject resultJson = RestfulUtil.getRestfulData(url, method, paramHeader, paramBody.toString());
		if (resultJson.get("responseCode").toString().startsWith("2")) {
			partner.setPassword(AesUtil.aesEncrypt(partner.getPassword()));
			if(partner.getId()==null){
				partner.setValid("1");
				partnerService.insertPartner(partner);
			}else{
				partnerService.updatePartner(partner);
			}
			message = "success";
		}else if(resultJson.get("responseCode").toString().startsWith("4")){
			message = "保存失败！请确认账号信息是否正确！";
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("partner", partner);
		mv.addObject("message", message);
		mv.setViewName("basic/partner");
		return mv;
	}
}
