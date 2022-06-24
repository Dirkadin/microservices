package com.dirkadin.inventory;

import com.dirkadin.clients.inventory.InventoryCheckResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/inventory")
public class InventoryController {

  private InventoryService inventoryService;

  @GetMapping("/{productId}")
  public InventoryCheckResponse checkInventory(@PathVariable("productId") Integer productId) {
    log.info("inventory request received for productId " + productId);
    return inventoryService.checkInventory();
  }
}
