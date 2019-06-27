package com.mbank.bank.service;

import com.mbank.bank.dto.TransactionDto;

public interface TransactionService {
	void sendTo(TransactionDto target);
}
