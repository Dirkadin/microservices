package com.dirkadin.inventory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory")
@Builder(toBuilder = true)
public class InventoryItem {

  @Id
  @SequenceGenerator(name = "inventory_id_sequence", sequenceName = "inventory_id_sequence")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id_sequence")
  private int productNumber;
  private String name;
  private double weightInLbs;
  private double price;
  private String notes;
}
