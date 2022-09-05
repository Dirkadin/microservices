package com.dirkadin.shipping.rabbitmq;

import com.dirkadin.clients.inventory.ShippingRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ShippingConsumer {

  @RabbitListener(queues = "${rabbitmq.queues.shipping}")
  public void consumer(ShippingRequest request) {
    log.info("Consumed {} from queue!", request);
    log.info("Shipped item!");
  }
}
