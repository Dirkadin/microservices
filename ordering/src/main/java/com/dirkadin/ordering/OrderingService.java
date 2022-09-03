package com.dirkadin.ordering;

import com.dirkadin.amqp.RabbitMQMessageProducer;
import com.dirkadin.clients.inventory.InventoryCheckResponse;
import com.dirkadin.clients.inventory.InventoryClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderingService {

  private final InventoryClient inventoryClient;
  private OrderingRepository orderingRepository;
  private RabbitMQMessageProducer mqProducer;

  public void placeOrder(OrderRequest orderRequest) {
    Order order = Order.builder()
        .emailAddress(orderRequest.getEmailAddress())
        .productId(orderRequest.getProductId())
        .quantity(orderRequest.getQuantity())
        .build();

    InventoryCheckResponse inventoryCheckResponse =
        inventoryClient.inventoryCheck(orderRequest.getProductId());

    if (inventoryCheckResponse.getQuantity() >= order.getQuantity()) {
      orderingRepository.saveAndFlush(order);
      mqProducer.publish(createOrderRequest(order),
          "internal.exchange",
          "internal.shipping.routing-key");
    } else {
      throw new OutOfStockException("Not enough inventory");
    }
  }

  private Object createOrderRequest(Order order) {
    return OrderRequest.builder()
        .productId(order.getProductId())
        .quantity(order.getQuantity())
        .emailAddress(order.getEmailAddress())
        .build();
  }
}
