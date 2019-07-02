package com.mbank.bank;

import com.mbank.bank.domain.CustomerEntity;
import com.mbank.bank.dto.CustomerDTO;

public class TransactionEntity {
	private CustomerEntity sendFrom;
	private CustomerEntity sendTo;
	private double amount;

	public TransactionEntity(CustomerEntity sendFrom, CustomerEntity sendTo, double amount) {
		this.sendFrom = sendFrom;
		this.sendTo = sendTo;
		this.amount = amount;
	}

	public CustomerEntity getSendFrom() {
		return sendFrom;
	}

	public CustomerEntity getSendTo() {
		return sendTo;
	}

	public double getAmount() {
		return amount;
	}
}
