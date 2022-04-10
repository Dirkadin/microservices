package com.dirkadin.ordering;

import com.dirkadin.clients.inventory.InventoryCheckResponse;
import com.dirkadin.clients.inventory.InventoryClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderingService {

  private final InventoryClient inventoryClient;
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
      orderingRepository.saveAndFlush(order);
    } else {
      throw new OutOfStockException("Not enough inventory");
    }
  }
}
