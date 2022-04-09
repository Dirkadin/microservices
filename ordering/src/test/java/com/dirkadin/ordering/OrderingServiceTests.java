package com.dirkadin.ordering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderingServiceTests {

  private OrderingService orderingService;

  @Mock
  private OrderingRepository orderingRepository;

  @Captor
  private ArgumentCaptor<Order> customerArgumentCaptor;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    orderingService = new OrderingService(orderingRepository);
  }

  @Test
  public void placeOrderTest() {
    //Given an order
    Order order = Order.builder()
        .productId(1)
        .quantity(1)
        .emailAddress("foo@bar.com")
        .build();

    //...a request
    OrderRequest orderRequest = OrderRequest.builder()
        .productId(order.getProductId())
        .quantity(order.getQuantity())
        .emailAddress(order.getEmailAddress())
        .build();

    //When we place the order
    orderingService.placeOrder(orderRequest);

    //Then nothing happens
    then(orderingRepository).should().saveAndFlush(customerArgumentCaptor.capture());
    Order orderArgumentCaptor = customerArgumentCaptor.getValue();
    assertThat(orderArgumentCaptor.getEmailAddress()).isEqualTo(order.getEmailAddress());
    assertThat(orderArgumentCaptor.getQuantity()).isEqualTo(order.getQuantity());
    assertThat(orderArgumentCaptor.getProductId()).isEqualTo(order.getProductId());
  }
}
