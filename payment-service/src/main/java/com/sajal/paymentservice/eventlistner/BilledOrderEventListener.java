package com.sajal.paymentservice.eventlistner;
import com.sajal.paymentservice.event.BilledOrderEvent;
import com.sajal.paymentservice.event.OrderCancelledEvent;
import com.sajal.paymentservice.util.Converter;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Log4j2
@Component
public class BilledOrderEventListener {
	private final RabbitTemplate rabbitTemplate;
	private final Converter converter;
	private final String queueBilledOrderName;
	private final String queueOrderCanceledName;
	public BilledOrderEventListener(RabbitTemplate rabbitTemplate,
			Converter converter,
			@Value("${queue.billed-order}") String queueBilledOrderName,
			@Value("${queue.order-canceled}") String queueOrderCanceledName) {

		this.rabbitTemplate = rabbitTemplate;
		this.converter = converter;
		this.queueBilledOrderName = queueBilledOrderName;
		this.queueOrderCanceledName = queueOrderCanceledName;
	}
	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onBilledOrderEvent(BilledOrderEvent event) {
		log.debug("Sending billed order event to {}, event: {}", queueBilledOrderName, event);
		rabbitTemplate.convertAndSend(queueBilledOrderName, converter.toJSON(event));
	}
	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	public void onOrderCancelledEvent(OrderCancelledEvent event) {
		log.debug("Sending order canceled event to {}, event: {}", queueOrderCanceledName, event);
		rabbitTemplate.convertAndSend(queueOrderCanceledName, converter.toJSON(event));
	}
}
