package com.dirkadin.ordering;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderingServiceTests {

  private OrderingController orderingController;

  @Mock
  private OrderingService sut;

//  @Captor
//  private ArgumentCaptor<OrderRequest> orderRequestArgumentCaptor;

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
