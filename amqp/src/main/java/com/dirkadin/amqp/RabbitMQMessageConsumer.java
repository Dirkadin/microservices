package com.dirkadin.amqp;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMQMessageConsumer {

  private final AmqpTemplate amqpTemplate;

  public Object consume() {
    return amqpTemplate.receiveAndConvert(1);
  }

}
