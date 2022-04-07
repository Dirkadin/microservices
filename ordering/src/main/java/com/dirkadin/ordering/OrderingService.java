package com.dirkadin.ordering;

import org.springframework.stereotype.Service;

@Service
public class OrderingService {

  public void placeOrder(OrderRequest orderRequest) {
    //todo: validate request
    //todo: check if we have inventory
  }
}
