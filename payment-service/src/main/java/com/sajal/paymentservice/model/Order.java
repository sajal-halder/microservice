package com.sajal.paymentservice.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Order {
	private Long id;
	private Long productId;
	private Long quantity;
	private BigDecimal value;
}
