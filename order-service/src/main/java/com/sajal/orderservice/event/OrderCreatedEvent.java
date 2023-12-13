package com.sajal.orderservice.event;

import com.sajal.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderCreatedEvent {
	private String transactionId;
	private Order order;
}