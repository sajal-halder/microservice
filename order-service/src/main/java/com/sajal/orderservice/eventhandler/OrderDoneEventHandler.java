package com.sajal.orderservice.eventhandler;

import com.sajal.orderservice.event.OrderDoneEvent;
import com.sajal.orderservice.service.OrderService;
import com.sajal.orderservice.util.Converter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class OrderDoneEventHandler {

	private final Converter converter;

	private final OrderService orderService;

	@RabbitListener(queues = {"${queue.order-done}"})
	public void handleOrderDoneEvent(@Payload String payload) {
		log.debug("Handling a order done event {}", payload);
		OrderDoneEvent event = converter.toObject(payload, OrderDoneEvent.class);
		orderService.updateOrderAsDone(event.getOrder().getId());
	}
}