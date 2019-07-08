package com.mbank.bank.service.impl;

import com.mbank.bank.EmailData;
import com.mbank.bank.TransactionEntity;
import com.mbank.bank.TransactionMessage;
import com.mbank.bank.service.EmailService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	public JavaMailSender emailSender;

	private final String invoice_template_path = "/jasper/invoice.jrxml";

	private Logger logger = LogManager.getLogger(EmailServiceImpl.class);

	@Override
	public void sendingEmail(TransactionEntity transaction) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(transaction.getSendTo().getEmail());
		message.setSubject(null);
		message.setText(TransactionMessage.transactionHeader(transaction));
		emailSender.send(message);
	}

	@Override
	public void sendRegistrationConfirmEmail(EmailData emailData) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailData.getUserEmail());
		message.setSubject(emailData.getSubject());
		message.setText(emailData.getConfirmUrl());
		emailSender.send(message);
	}

	@Override
	public void sendMessageWithAttachment(TransactionEntity transaction) throws MessagingException, IOException {
		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(transaction.getSendTo().getEmail());
		helper.setSubject("Invoice");
		helper.setText(TransactionMessage.transactionHeader(transaction));


		helper.addAttachment("invoice", generatePdf(transaction));
		emailSender.send(message);

	}

	private File generatePdf(TransactionEntity transaction) throws IOException{
		File pdfFile = File.createTempFile("my-invoice", ".pdf");

		try(FileOutputStream pos = new FileOutputStream(pdfFile)) {
			final JasperReport report = loadTemplate();
			final Map<String, String> parameters = parameters(transaction);

			final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Invoice"));

			JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);

		}
		catch (IOException | JRException e) {
			logger.error(e.getMessage());
		}
		return pdfFile;
	}

	private Map<String, String> parameters(TransactionEntity transaction) {
		final Map<String, String> parameters = new HashMap<>();

		parameters.put("userFrom",  transaction.getSendFrom().getLogin());
		parameters.put("userTo", transaction.getSendTo().getLogin());
		parameters.put("amount", String.valueOf(transaction.getAmount()));

		return parameters;
	}

	private JasperReport loadTemplate() throws JRException {
		final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template_path);

		final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

		return JasperCompileManager.compileReport(jasperDesign);
	}
}
