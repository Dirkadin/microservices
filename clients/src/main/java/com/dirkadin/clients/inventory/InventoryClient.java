package com.dirkadin.clients.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "inventory",
    url = "${clients.inventory.url}"
)
public interface InventoryClient {

  @GetMapping(path = "api/v1/inventory/{productId}")
  InventoryCheckResponse inventoryCheck(
      @PathVariable("productId") Integer productId);

}
