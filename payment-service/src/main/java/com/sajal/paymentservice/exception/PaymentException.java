package com.sajal.paymentservice.exception;

import java.io.Serial;

public class PaymentException extends RuntimeException{
	@Serial
	private static final long serialVersionUID = 1L;

	public PaymentException(String message) {
		super(message);
	}
}
