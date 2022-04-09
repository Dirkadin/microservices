package com.dirkadin.ordering;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

  @Min(1)
  @NotNull(message = "productId should not be null")
  private int productId;

  @Min(1)
  @NotNull(message = "quantity should not be null")
  private int quantity;

  @NotBlank(message = "emailAddress cannot be empty or null")
  @Email(message = "emailAddress is not valid")
  private String emailAddress;
}
