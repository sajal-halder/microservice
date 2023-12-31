package com.sajal.stockservice.util;

import org.springframework.stereotype.Component;

@Component
public class TransactionHolder {
	private final ThreadLocal<String> currentTransactionId = new ThreadLocal<>();
	public String getCurrentTransactionId() {

		return currentTransactionId.get();
	}

	public void setCurrentTransactionId(String transactionId) {

		currentTransactionId.set(transactionId);
	}
}
