package com.customers.rewards.model;

import com.customers.rewards.dto.TransactionDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long transactionId;

  private String customerId;

  private Double amount;

  private LocalDateTime transactionDate;

  public static Transaction getTransactionEntity(TransactionDTO transactionDTO) {
    Transaction transaction = new Transaction();
    transaction.setCustomerId(transactionDTO.getCustomerId());
    transaction.setAmount(transactionDTO.getAmount());
    transaction.setTransactionDate(transactionDTO.getTransactionDate());
    return transaction;
  }
}
