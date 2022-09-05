package com.dirkadin.shipping;

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
public class ShippingConfig {

  @Value("${rabbitmq.exchanges.internal}")
  private String internalExchange;

  @Value("${rabbitmq.queues.shipping}")
  private String shippingQueue;

  @Value(("${rabbitmq.routing-keys.internal-shipping}"))
  private String internalShippingRoutingKey;

  @Bean
  public TopicExchange internalTopicExchange() {
    return new TopicExchange(this.internalExchange);
  }

  @Bean
  public Queue shippingQueue() {
    return new Queue(this.shippingQueue);
  }

  @Bean
  public Binding internalToShippingBinding() {
    return BindingBuilder.bind(shippingQueue()).to(internalTopicExchange())
        .with(this.internalShippingRoutingKey);
  }
}
