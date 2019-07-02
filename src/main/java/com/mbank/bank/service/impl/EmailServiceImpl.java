package com.mbank.bank.service.impl;

import com.mbank.bank.FileHelper;
import com.mbank.bank.TransactionEntity;
import com.mbank.bank.TransactionMessage;
import com.mbank.bank.TransactionPdf;
import com.mbank.bank.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	public JavaMailSender emailSender;

	@Override
	public void sendingEmail(TransactionEntity transaction) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(transaction.getSendTo().getEmail());
		message.setSubject(null);
		message.setText(TransactionMessage.transactionHeader(transaction));
		emailSender.send(message);
	}

	@Override
	public void sendMessageWithAttachment(TransactionEntity transaction) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(transaction.getSendTo().getEmail());
		helper.setSubject("Paid");
		helper.setText(TransactionMessage.transactionHeader(transaction));

		File file = FileHelper.generateFile();
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		helper.addAttachment("Invoice", file);

		try {
			TransactionPdf.createPdf(file, transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}

		emailSender.send(message);
	}
}
