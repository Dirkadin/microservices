package com.dirkadin.clients.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
    name = "shipping",
    url = "${clients.shipping.url}"
)
public interface ShippingClient {

  @PostMapping(path = "api/v1/shipping/shiporder/{orderId}")
  void shipOrder(@PathVariable("orderId") Integer orderId);
}
