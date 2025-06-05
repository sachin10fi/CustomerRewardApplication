package com.customers.rewards.service;

import static org.junit.Assert.*;

import com.customers.rewards.dto.TransactionDTO;
import com.customers.rewards.exception.CustomerNotFoundException;
import com.customers.rewards.repository.CustomerRepository;
import com.customers.rewards.repository.TransactionRepository;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

  @Mock private CustomerRepository customerRepository;

  @Mock private TransactionRepository transactionRepository;

  @InjectMocks private TransactionService transactionService;

  @Test
  public void testSaveTransactionSuccess() throws CustomerNotFoundException {
    Mockito.when(customerRepository.existsById(Mockito.anyString())).thenReturn(true);
    transactionService.saveTransaction(getTransactionDTO());
    Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test(expected = CustomerNotFoundException.class)
  public void testSaveTransactionCustomerNotFoundException() throws CustomerNotFoundException {
    Mockito.when(customerRepository.existsById(Mockito.anyString())).thenReturn(false);
    transactionService.saveTransaction(getTransactionDTO());
  }

  private TransactionDTO getTransactionDTO() {
    TransactionDTO transactionDTO = new TransactionDTO();
    transactionDTO.setTransactionDate(LocalDateTime.now());
    transactionDTO.setAmount(12.00);
    transactionDTO.setCustomerId("123");
    return transactionDTO;
  }
}
