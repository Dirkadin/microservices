package com.dirkadin.ordering;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderingServiceTests {

  private OrderingController orderingController;

  @Mock
  private OrderingService sut;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    orderingController = new OrderingController(sut);
  }

  @Test
  public void placeOrderTest() {
    //Given an order request
    OrderRequest orderRequest = new OrderRequest(1, 1, "foo@bar.com");
    //When we place the order
    orderingController.placeOrder(orderRequest);
    //Then nothing happens
  }
}
