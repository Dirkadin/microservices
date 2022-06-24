package com.dirkadin.inventory;

import com.dirkadin.clients.inventory.InventoryCheckResponse;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryService {

  private InventoryRepository inventoryRepository;

  public InventoryCheckResponse checkInventory() {
    Random rand = new Random();
    int randomNumber = rand.nextInt((10) + 1);

    InventoryItem item = InventoryItem.builder()
        .name("foo bar")
        .notes("notes")
        .price(420.69)
        .productNumber(3)
        .weightInLbs(101.1)
        .build();

    inventoryRepository.save(item);

    return new InventoryCheckResponse(randomNumber);
  }

  public void holdInventory() {
  }

  public void removeInventory() {
  }
}
