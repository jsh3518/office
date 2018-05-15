package com.office.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.office.entity.Mailbox;
import com.office.service.MailboxService;
import com.office.util.AesUtil;

@Controller
@RequestMapping(value = "/mailbox")
public class MailboxController {

	@Autowired
	private MailboxService mailboxService;

	/**
	 * 显示Mailbox Center 账号信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping
	public ModelAndView getMailbox() {
		Mailbox mailbox = new Mailbox();
		mailbox = mailboxService.getMailbox(mailbox);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("mailbox", mailbox);
		mv.setViewName("basic/mailbox");
		return mv;
	}

	/**
	 * 更新Mailbox Center 账号信息
	 * @param mailbox
	 * @return
	 */
	@RequestMapping(value = "/editMailbox")
	public ModelAndView editMailbox(Mailbox mailbox) {
		
		//对发件人密码进行加密处理
		mailbox.setPassword(AesUtil.aesEncrypt(mailbox.getPassword()));
		if(mailbox.getId()==null){
			mailboxService.insertMailbox(mailbox);
		}else{
			mailboxService.updateMailbox(mailbox);
		}

		ModelAndView mv = new ModelAndView();
		mv.addObject("mailbox", mailbox);
		mv.addObject("message", "success");
		mv.setViewName("basic/mailbox");
		return mv;
	}
}
