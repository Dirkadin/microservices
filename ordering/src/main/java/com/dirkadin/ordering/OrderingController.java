package com.dirkadin.ordering;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/order")
public class OrderingController {

  private final OrderingService orderingService;

  @PostMapping("/placeorder")
  public ResponseEntity<OrderRequest> placeOrder(@RequestBody @Valid OrderRequest orderRequest) {
    log.info("new order received {}", orderRequest);
    orderingService.placeOrder(orderRequest);
    return new ResponseEntity<>(orderRequest, HttpStatus.CREATED);
  }
}
