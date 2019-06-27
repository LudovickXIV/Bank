package com.mbank.bank.repository;

import com.mbank.bank.domain.CustomerEntity;
import com.mbank.bank.domain.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
	Optional<TokenEntity> findByToken(String token);
	void deleteAllByCustomerEntity(CustomerEntity customerEntity);
}
