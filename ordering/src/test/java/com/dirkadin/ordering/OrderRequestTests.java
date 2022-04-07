package com.dirkadin.ordering;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderRequestTests {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void emailIsInvalid() {
    //Given an order request
    OrderRequest orderRequest = new OrderRequest(1, 1, "foo");
    //When
    //Then there is one validation error stating "email is not valid"
    Set<ConstraintViolation<OrderRequest>> validationErrors = validator.validate(orderRequest);
    assertFalse(validator.validate(orderRequest).isEmpty());
    assertTrue(validationErrors.stream().anyMatch(orderRequestConstraintViolation ->
        orderRequestConstraintViolation.getMessage().equals("emailAddress is not valid")));
  }

  @Test
  public void emailsIsNull() {
    //Given an order request
    OrderRequest orderRequest = new OrderRequest(1, 1, null);
    //When
    //Then there is one validation error stating "email is not valid"
    Set<ConstraintViolation<OrderRequest>> validationErrors = validator.validate(orderRequest);
    assertFalse(validator.validate(orderRequest).isEmpty());
    assertTrue(validationErrors.stream().anyMatch(orderRequestConstraintViolation ->
        orderRequestConstraintViolation.getMessage()
            .equals("emailAddress cannot be empty or blank")));
  }

}
