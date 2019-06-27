package com.mbank.bank.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthorizationException extends AuthenticationException {
	public AuthorizationException(String message) {
		super(message);
	}
}
