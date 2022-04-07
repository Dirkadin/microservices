package com.dirkadin.ordering;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

  @NotNull(message = "productId should not be null")
  private int productId;
  @NotNull(message = "quantity should not be null")
  @Min(1)
  private int quantity;
  @NotBlank(message = "emailAddress cannot be empty or blank")
  @Email(message = "emailAddress is not valid")
  private String emailAddress;
}
