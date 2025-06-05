package com.customers.rewards.service;

import com.customers.rewards.dto.TransactionDTO;
import com.customers.rewards.exception.CustomerNotFoundException;
import com.customers.rewards.model.Transaction;
import com.customers.rewards.repository.CustomerRepository;
import com.customers.rewards.repository.TransactionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

  @Autowired private CustomerRepository customerRepository;

  @Autowired private TransactionRepository transactionRepository;

  @CircuitBreaker(name = "saveTransaction", fallbackMethod = "saveTransactionFallback")
  public void saveTransaction(TransactionDTO transactionDTO) throws CustomerNotFoundException {
    if (!checkCustomer(transactionDTO.getCustomerId())) {
      logger.error("Customer Doesn't exist");
      throw new CustomerNotFoundException("Customer Doesn't exist");
    }
    Transaction transaction = Transaction.getTransactionEntity(transactionDTO);
    transactionRepository.save(transaction);
    logger.info("Saved transaction details in db {}", transaction);
  }

  public void saveTransactionFallback(TransactionDTO transactionDTO, Throwable t) {
    logger.error(
        "Inside Fallback of saveTransaction for Customer:{} with error:{}",
        transactionDTO.getCustomerId(),
        t.getMessage());
  }

  // Checking Valid Customer
  private boolean checkCustomer(String customerId) {
    return customerRepository.existsById(customerId);
  }
}
