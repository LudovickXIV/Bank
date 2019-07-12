package com.mbank.bank;

import com.mbank.bank.domain.CustomerEntity;
import com.mbank.bank.domain.VerificationToken;
import com.mbank.bank.repository.VerificationRepository;
import com.mbank.bank.service.CustomerService;
import com.mbank.bank.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private EmailService emailService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private VerificationRepository verificationRepository;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		CustomerEntity user = event.getCustomerEntity();
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(token, user);
		verificationRepository.save(verificationToken);
//		service.createVerificationToken(user, token);


		String recipientAddress = user.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl
				= event.getAppUrl() + "/customer/registrationConfirm?token=" + token;

		emailService.sendRegistrationConfirmEmail(new EmailData(recipientAddress, subject, confirmationUrl));

	}
}
