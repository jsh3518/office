package com.office.mapper;

import com.office.entity.Mailbox;

public interface MailboxMapper  {

	public Mailbox getMailbox(Mailbox mailbox);
	
	public void insertMailbox(Mailbox mailbox);
	
	public void updateMailbox(Mailbox mailbox);

}
