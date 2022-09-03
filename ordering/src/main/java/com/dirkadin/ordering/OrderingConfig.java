package com.dirkadin.ordering;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class OrderingConfig {

  @Value("${rabbitmq.exchanges.internal}")
  private String internalExchange;

  @Value("${rabbitmq.queues.ordering}")
  private String orderingQueue;

  @Value(("${rabbitmq.routing-keys.internal-ordering"))
  private String internalOrderingRoutingKey;
}
