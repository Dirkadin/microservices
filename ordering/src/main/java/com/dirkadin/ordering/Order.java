package com.dirkadin.ordering;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Order {

  private int orderId;
  private int productId;
  private int quantity;
  private String emailAddress;
}
