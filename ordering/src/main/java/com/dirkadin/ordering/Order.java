package com.dirkadin.ordering;

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

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ordering") //order is a protected table name by postgres
@Builder(toBuilder = true)
public class Order {

  @Id
  @SequenceGenerator(
      name = "ordering_id_sequence",
      sequenceName = "ordering_id_sequence"
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "ordering_id_sequence"
  )
  private int orderId;
  private int productId;
  private int quantity;
  private String emailAddress;
}
