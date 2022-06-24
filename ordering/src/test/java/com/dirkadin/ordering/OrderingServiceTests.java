package com.dirkadin.ordering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.verifyNoInteractions;

import com.dirkadin.amqp.RabbitMQMessageProducer;
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

  @Mock
  private RabbitMQMessageProducer rabbitMqMessageProducer;

  @Captor
  private ArgumentCaptor<Order> orderArgumentCaptor;

  @Captor
  private ArgumentCaptor<Integer> productIdArgumentCaptor;

  @Captor
  private ArgumentCaptor<OrderRequest> orderRequestCaptor;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    orderingService =
        new OrderingService(inventoryClient, orderingRepository, rabbitMqMessageProducer);
  }

  @Test
  public void happyPath() {
    // Given an order
    Order order = Order.builder().productId(1).quantity(1).emailAddress("foo@bar.com").build();

    // ...a request
    OrderRequest orderRequest =
        OrderRequest.builder()
            .productId(order.getProductId())
            .quantity(order.getQuantity())
            .emailAddress(order.getEmailAddress())
            .build();

    // ...there is inventory
    when(inventoryClient.inventoryCheck(anyInt())).thenReturn(new InventoryCheckResponse(10));

    // When we place the order
    orderingService.placeOrder(orderRequest);

    // Then the inventory is checked
    then(inventoryClient).should().inventoryCheck(productIdArgumentCaptor.capture());
    assertThat(productIdArgumentCaptor.getValue()).isEqualTo(order.getProductId());

    // Then we save the order to the order repository
    then(orderingRepository).should().saveAndFlush(orderArgumentCaptor.capture());
    Order orderArgumentCaptor = this.orderArgumentCaptor.getValue();
    assertThat(orderArgumentCaptor.getEmailAddress()).isEqualTo(order.getEmailAddress());
    assertThat(orderArgumentCaptor.getQuantity()).isEqualTo(order.getQuantity());
    assertThat(orderArgumentCaptor.getProductId()).isEqualTo(order.getProductId());

    // Then the order sent to the queue should match the order request sent to the database
    then(rabbitMqMessageProducer)
        .should()
        .publish(orderRequestCaptor.capture(), anyString(), anyString());
    assertThat(orderArgumentCaptor.getProductId())
        .isEqualTo(orderRequestCaptor.getValue().getProductId());
    assertThat(orderArgumentCaptor.getQuantity())
        .isEqualTo(orderRequestCaptor.getValue().getQuantity());
    assertThat(orderArgumentCaptor.getEmailAddress())
        .isEqualTo(orderRequestCaptor.getValue().getEmailAddress());
  }

  @Test
  public void outOfStock() {
    // Given an order
    Order order = Order.builder().productId(1).quantity(1).emailAddress("foo@bar.com").build();

    // ...a request
    OrderRequest orderRequest =
        OrderRequest.builder()
            .productId(order.getProductId())
            .quantity(order.getQuantity())
            .emailAddress(order.getEmailAddress())
            .build();

    // ... there is no inventory available
    when(inventoryClient.inventoryCheck(anyInt())).thenReturn(new InventoryCheckResponse(0));

    // When we place the order
    // Then an exception will be thrown
    assertThrows(OutOfStockException.class, () -> orderingService.placeOrder(orderRequest));
    // Then repository will not be called
    verifyNoInteractions(orderingRepository);
  }
}
