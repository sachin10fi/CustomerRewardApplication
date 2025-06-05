package com.customers.rewards.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

  @NotNull(message = "Customer Id cannot be null")
  private String customerId;

  private Double amount;

  private LocalDateTime transactionDate;
}
