package com.mbank.bank.service.impl;

import com.mbank.bank.TransactionEntity;
import com.mbank.bank.domain.CustomerEntity;
import com.mbank.bank.dto.TransactionDto;
import com.mbank.bank.repository.CustomerRepository;
import com.mbank.bank.security.Principal;
import com.mbank.bank.service.AuthService;
import com.mbank.bank.service.EmailService;
import com.mbank.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private CustomerRepository repository;

	@Autowired
	private AuthService authService;

	@Autowired
	private EmailService emailService;

	@Override
	public void sendTo(TransactionDto target) {
		Principal principal = (Principal) authService.getAuthPrincipal();
		CustomerEntity sendTarget = repository.findByLogin(target.getUsernameTo());
		CustomerEntity sendFrom = repository.findByLogin(principal.getLogin());
		sendFrom.setMoney(sendFrom.getMoney() - target.getAmount());
		sendTarget.setMoney(sendTarget.getMoney() + target.getAmount());

		try {
			emailService.sendMessageWithAttachment(new TransactionEntity(sendFrom, sendTarget, target.getAmount()));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
