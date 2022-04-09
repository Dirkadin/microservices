package com.dirkadin.ordering;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderingService {

  private OrderingRepository orderingRepository;

  public void placeOrder(OrderRequest orderRequest) {
    Order order = Order.builder()
        .emailAddress(orderRequest.getEmailAddress())
        .productId(orderRequest.getProductId())
        .quantity(orderRequest.getQuantity())
        .build();

    //todo: validate request
    //todo: check if we have inventory
    //todo: save to repository
    orderingRepository.saveAndFlush(order);
  }
}
