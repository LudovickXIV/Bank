package com.mbank.bank.service;

import com.mbank.bank.domain.CustomerEntity;
import com.mbank.bank.domain.TokenEntity;

import java.util.Optional;

public interface TokenService {
	String createUserToken(CustomerEntity customerEntity);
	Optional<TokenEntity> findByToken(String token);
	void delete(TokenEntity tokenEntity);
	void deleteTokenByUser(CustomerEntity customerEntity);
}
