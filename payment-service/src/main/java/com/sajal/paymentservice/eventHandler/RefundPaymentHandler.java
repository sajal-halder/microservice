package com.sajal.paymentservice.eventHandler;

import com.sajal.paymentservice.event.OrderCreatedEvent;
import com.sajal.paymentservice.event.RefundPaymentEvent;
import com.sajal.paymentservice.service.PaymentService;
import com.sajal.paymentservice.util.Converter;
import com.sajal.paymentservice.util.TransactionHolder;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class RefundPaymentHandler {
	private final Converter converter;
	private final PaymentService paymentService;
	private final TransactionHolder transactionIdHolder;

	@RabbitListener(queues = {"${queue.refund-payment}"})
	public void onRefundPayment(@Payload String payload) {
		log.debug("Handling a refund order event {}", payload);
		RefundPaymentEvent event = converter.toObject(payload, RefundPaymentEvent.class);
		transactionIdHolder.setCurrentTransactionId(event.getTransactionId());
		paymentService.refund(event.getOrder().getId());
	}
}