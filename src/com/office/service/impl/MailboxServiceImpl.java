package com.office.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.entity.Mailbox;
import com.office.mapper.MailboxMapper;
import com.office.service.MailboxService;

@Service("MailboxService")
public class MailboxServiceImpl implements MailboxService {
	
	@Resource(name="mailboxMapper")
	@Autowired
	private MailboxMapper mailboxMapper;

	public MailboxMapper getMailboxMapper() {
		return mailboxMapper;
	}

	public void setMailboxMapper(MailboxMapper mailboxMapper) {
		this.mailboxMapper = mailboxMapper;
	}

	/**
	 * 查询mailbox
	 * @param mailbox
	 * @return
	 */
	public Mailbox getMailbox(Mailbox mailbox){
		return mailboxMapper.getMailbox(mailbox);
	}

	/**
	 * 新增mailbox
	 * @param mailbox
	 */
	public void insertMailbox(Mailbox mailbox) {
		mailboxMapper.insertMailbox(mailbox);
	}

	/**
	 * 更新mailbox
	 * @param mailbox
	 */
	public void updateMailbox(Mailbox mailbox) {
		mailboxMapper.updateMailbox(mailbox);
	}
}
