package com.customers.rewards.service;

import static org.junit.Assert.*;

import com.customers.rewards.exception.CustomerNotFoundException;
import com.customers.rewards.model.Transaction;
import com.customers.rewards.repository.CustomerRepository;
import com.customers.rewards.repository.TransactionRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RewardsServiceTest {

  @Mock private CustomerRepository customerRepository;

  @Mock private TransactionRepository transactionRepository;

  @InjectMocks private RewardsService rewardsService;

  @Test
  public void tesGetTotalRewardsSuccess() throws CustomerNotFoundException {
    Mockito.when(customerRepository.existsById(Mockito.anyString())).thenReturn(true);
    Mockito.when(
            transactionRepository.findByCustomerIdAndCreatedAtBetween(
                Mockito.anyString(), Mockito.any(), Mockito.any()))
        .thenReturn(getTransactions());
    assertEquals(
        6960,
        rewardsService
            .getTotalRewards("123", LocalDate.MIN, LocalDate.now())
            .getTotalRewardPoints()
            .intValue());
  }

  @Test(expected = CustomerNotFoundException.class)
  public void tesGetTotalRewardsCustomerNotFoundException() throws CustomerNotFoundException {
    Mockito.when(customerRepository.existsById(Mockito.anyString())).thenReturn(false);
    rewardsService.getTotalRewards("123", LocalDate.MIN, LocalDate.now());
  }

  @Test(expected = IllegalArgumentException.class)
  public void tesGetTotalRewardsIllegalArgumentException() throws CustomerNotFoundException {
    Mockito.when(customerRepository.existsById(Mockito.anyString())).thenReturn(true);
    rewardsService.getTotalRewards("123", null, LocalDate.now());
  }

  private List<Transaction> getTransactions() {
    Transaction transaction1 = new Transaction();
    transaction1.setTransactionDate(LocalDateTime.now());
    transaction1.setAmount(1235.00);
    transaction1.setTransactionId(12L);
    transaction1.setCustomerId("123");
    Transaction transaction2 = new Transaction();
    transaction2.setTransactionDate(LocalDateTime.now());
    transaction2.setAmount(1235.00);
    transaction2.setTransactionId(12L);
    transaction2.setCustomerId("123");
    Transaction transaction3 = new Transaction();
    transaction3.setTransactionDate(LocalDateTime.MIN);
    transaction3.setAmount(1235.00);
    transaction3.setTransactionId(12L);
    transaction3.setCustomerId("123");
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(transaction1);
    transactions.add(transaction2);
    transactions.add(transaction3);
    return transactions;
  }
}
