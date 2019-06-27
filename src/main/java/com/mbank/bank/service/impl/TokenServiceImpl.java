package com.mbank.bank.service.impl;

import com.mbank.bank.domain.CustomerEntity;
import com.mbank.bank.domain.TokenEntity;
import com.mbank.bank.repository.TokenRepository;
import com.mbank.bank.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	@Value("${security.token-lifetime-days}")
	private Integer lifeTimeDays;

	@Override
	public String createUserToken(CustomerEntity customerEntity) {
		String token = UUID.randomUUID().toString();

		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setToken(token);
		tokenEntity.setCustomerEntity(customerEntity);
		tokenEntity.setExpirationDate(LocalDateTime.now().plusDays(lifeTimeDays));
		tokenRepository.save(tokenEntity);
		return token;
	}

	@Override
	public Optional<TokenEntity> findByToken(String token) {
		return tokenRepository.findByToken(token);
	}

	@Override
	public void delete(TokenEntity tokenEntity) {
		tokenRepository.delete(tokenEntity);
	}

	@Override
	public void deleteTokenByUser(CustomerEntity customerEntity) {
		tokenRepository.deleteAllByCustomerEntity(customerEntity);
	}
}
