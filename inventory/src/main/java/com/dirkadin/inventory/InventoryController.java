package com.dirkadin.inventory;

import com.dirkadin.clients.inventory.InventoryCheckResponse;
import java.util.Random;
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

  @GetMapping("/{productId}")
  public InventoryCheckResponse checkInventory(@PathVariable("productId") Integer productId) {
    log.info("inventory request received for productId " + productId);
    Random rand = new Random();
    int randomNumber = rand.nextInt((10) + 1);
    return new InventoryCheckResponse(randomNumber);
  }

}
