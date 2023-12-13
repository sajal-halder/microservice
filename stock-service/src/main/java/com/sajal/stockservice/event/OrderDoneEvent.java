package com.sajal.stockservice.event;

import com.sajal.stockservice.model.Order;
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
public class OrderDoneEvent {
	private String transactionId;
	private Order order;
}
