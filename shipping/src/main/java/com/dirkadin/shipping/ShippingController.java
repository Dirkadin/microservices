package com.dirkadin.shipping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/shipping")
public class ShippingController {

  @PostMapping("shiporder/{orderid}")
  public void shipOrder(@PathVariable String orderid) {
    log.info("shipping order: " + orderid);
  }
}
