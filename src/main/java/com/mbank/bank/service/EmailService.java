package com.mbank.bank.service;

import com.mbank.bank.EmailData;
import com.mbank.bank.TransactionEntity;
import com.mbank.bank.dto.CustomerDTO;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {
	void sendingEmail(TransactionEntity transaction);
	void sendRegistrationConfirmEmail(EmailData emailData);
	void sendMessageWithAttachment(TransactionEntity transaction)
			throws MessagingException, IOException;
}
