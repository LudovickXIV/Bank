package com.mbank.bank;

public class TransactionMessage {

	public static String transactionHeader(TransactionEntity transaction) {
		return "This money from " + transaction.getSendFrom().getEmail()
				+ " (" + transaction.getSendFrom().getLogin() + ") "
				+ " amount: " + transaction.getAmount();
	}
}
