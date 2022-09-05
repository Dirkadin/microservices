package com.dirkadin.ordering;

import com.dirkadin.amqp.RabbitMqMessageProducer;
import com.dirkadin.clients.inventory.InventoryCheckResponse;
import com.dirkadin.clients.inventory.InventoryClient;
import com.dirkadin.clients.inventory.ShippingRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderingService {

  private final InventoryClient inventoryClient;
  private final RabbitMqMessageProducer mqProducer;
  private OrderingRepository orderingRepository;

  public void placeOrder(OrderRequest orderRequest) {
    Order order = Order.builder()
        .emailAddress(orderRequest.getEmailAddress())
        .productId(orderRequest.getProductId())
        .quantity(orderRequest.getQuantity())
        .build();

    InventoryCheckResponse inventoryCheckResponse;
    try {
      inventoryCheckResponse =
          inventoryClient.inventoryCheck(orderRequest.getProductId());
    } catch (Exception e) {
      inventoryCheckResponse = new InventoryCheckResponse(0);
    }


    if (inventoryCheckResponse.getQuantity() >= order.getQuantity()) {
      orderingRepository.saveAndFlush(order);
      mqProducer.publish(createShippingRequest(order),
          "internal.exchange",
          "internal.shipping.routing-key");
    } else {
      throw new OutOfStockException("Not enough inventory");
    }
  }

  private Object createShippingRequest(Order order) {
    return new ShippingRequest(order.getEmailAddress());
  }
}
