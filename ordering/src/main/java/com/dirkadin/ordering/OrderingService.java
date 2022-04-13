package com.dirkadin.ordering;

import com.dirkadin.clients.inventory.InventoryCheckResponse;
import com.dirkadin.clients.inventory.InventoryClient;
import com.dirkadin.clients.inventory.ShippingClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderingService {

  private final InventoryClient inventoryClient;
  private final ShippingClient shippingClient;
  private OrderingRepository orderingRepository;

  public void placeOrder(OrderRequest orderRequest) {
    Order order = Order.builder()
        .emailAddress(orderRequest.getEmailAddress())
        .productId(orderRequest.getProductId())
        .quantity(orderRequest.getQuantity())
        .build();

    //todo: validate request
    //todo: check if we have inventory
    InventoryCheckResponse inventoryCheckResponse = inventoryClient.inventoryCheck(
        orderRequest.getProductId());

    orderingRepository.saveAndFlush(order);

    if (inventoryCheckResponse.getQuantity() >= order.getQuantity()) {
      order = orderingRepository.saveAndFlush(order);
      shippingClient.shipOrder(order.getOrderId());
    } else {
      throw new OutOfStockException("Not enough inventory");
    }
  }
}
