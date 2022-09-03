package com.dirkadin.ordering;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
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

  @Bean
  public TopicExchange internalTopicExchange() {
    return new TopicExchange(this.internalExchange);
  }

  @Bean
  public Queue orderingQueue() {
    return new Queue(this.orderingQueue);
  }

  @Bean
  public Binding internalToOrderingBinding() {
    return BindingBuilder
        .bind(orderingQueue())
        .to(internalTopicExchange())
        .with(this.internalOrderingRoutingKey);
  }
}
