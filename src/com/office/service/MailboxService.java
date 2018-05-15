package com.office.service;

import com.office.entity.Mailbox;

public interface MailboxService {
	
	/*
	 * 查询mailbox
	 */
	public Mailbox getMailbox(Mailbox mailbox);

	/*
	 * 新增mailbox
	 */
	public void insertMailbox(Mailbox mailbox);
	
	/*
	 * 更新mailbox
	 */
	public void updateMailbox(Mailbox mailbox);
}
