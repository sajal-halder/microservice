package com.sajal.stockservice.eventhandler;

import com.sajal.stockservice.event.BilledOrderEvent;
import com.sajal.stockservice.exception.StockException;
import com.sajal.stockservice.service.StockService;
import com.sajal.stockservice.util.Converter;
import com.sajal.stockservice.util.TransactionHolder;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class EventHandler {
	private final Converter converter;
	private final StockService stockService;
	private final TransactionHolder transactionHolder;

	@RabbitListener(queues = {"${queue.billed-order}"})
	public void handle(@Payload String payload) {
		log.debug("Handling a billed order event {}", payload);
		BilledOrderEvent event = converter.toObject(payload, BilledOrderEvent.class);
		transactionHolder.setCurrentTransactionId(event.getTransactionId());
		try {
			stockService.updateQuantity(event.getOrder());
		} catch (StockException e) {
			log.error("Cannot update stock, reason: {}", e.getMessage());
		}
	}
}
