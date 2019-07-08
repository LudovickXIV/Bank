package com.mbank.bank;

public class EmailData {
	private String userEmail;
	private String subject;
	private String confirmUrl;

	public EmailData(String userEmail, String subject, String confirmUrl) {
		this.userEmail = userEmail;
		this.subject = subject;
		this.confirmUrl = confirmUrl;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getSubject() {
		return subject;
	}

	public String getConfirmUrl() {
		return confirmUrl;
	}
}
