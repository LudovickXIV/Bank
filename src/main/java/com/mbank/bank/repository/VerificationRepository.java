package com.mbank.bank.repository;

import com.mbank.bank.domain.CustomerEntity;
import com.mbank.bank.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.stream.Stream;

public interface VerificationRepository extends JpaRepository<VerificationToken, Long> {
	VerificationToken findByToken(String token);

	VerificationToken findByUser(CustomerEntity customer);

	Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

	void deleteByExpiryDateLessThan(Date now);
}
