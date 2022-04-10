package com.dirkadin.ordering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.when;

import com.dirkadin.clients.inventory.InventoryCheckResponse;
import com.dirkadin.clients.inventory.InventoryClient;
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

  @Mock
  private InventoryClient inventoryClient;

  @Captor
  private ArgumentCaptor<Order> orderArgumentCaptor;

  @Captor
  private ArgumentCaptor<Integer> productIdArgumentCaptor;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    orderingService = new OrderingService(inventoryClient, orderingRepository);
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

    //...there is inventory
    when(inventoryClient.inventoryCheck(anyInt())).thenReturn(new InventoryCheckResponse(10));

    //When we place the order
    orderingService.placeOrder(orderRequest);

    //Then nothing happens
    then(inventoryClient).should().inventoryCheck(productIdArgumentCaptor.capture());
    assertThat(productIdArgumentCaptor.getValue()).isEqualTo(order.getProductId());

    then(orderingRepository).should().saveAndFlush(orderArgumentCaptor.capture());
    Order orderArgumentCaptor = this.orderArgumentCaptor.getValue();
    assertThat(orderArgumentCaptor.getEmailAddress()).isEqualTo(order.getEmailAddress());
    assertThat(orderArgumentCaptor.getQuantity()).isEqualTo(order.getQuantity());
    assertThat(orderArgumentCaptor.getProductId()).isEqualTo(order.getProductId());
  }

  @Test

  public void outOfStock() {
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

    //... there is no inventory available
    when(inventoryClient.inventoryCheck(anyInt())).thenReturn(new InventoryCheckResponse(0));

    //When we place the order
    //Then an exception will be thrown
    Exception exception = assertThrows(OutOfStockException.class, () -> {
      orderingService.placeOrder(orderRequest);
    });
  }
}
