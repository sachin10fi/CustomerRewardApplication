package com.customers.rewards.controller;

import com.customers.rewards.dto.TransactionDTO;
import com.customers.rewards.exception.CustomerNotFoundException;
import com.customers.rewards.service.TransactionService;
import java.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

  @Mock private TransactionService transactionService;

  @InjectMocks private TransactionController transactionController;

  @Test
  public void testSaveTransactionSuccess() throws CustomerNotFoundException {
    Mockito.doNothing().when(transactionService).saveTransaction(getTransactionDTO());
    Assert.assertEquals(
        HttpStatus.OK, transactionController.saveTransaction(getTransactionDTO()).getStatusCode());
  }

  @Test
  public void testSaveTransactionCustomerNotFoundException() throws CustomerNotFoundException {
    Mockito.doThrow(new CustomerNotFoundException("msg"))
        .when(transactionService)
        .saveTransaction(Mockito.any());
    Assert.assertEquals(
        HttpStatus.NOT_FOUND,
        transactionController.saveTransaction(getTransactionDTO()).getStatusCode());
  }

  @Test
  public void testSaveTransactionException() throws CustomerNotFoundException {
    Mockito.doThrow(new NullPointerException("msg"))
        .when(transactionService)
        .saveTransaction(Mockito.any());
    Assert.assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR,
        transactionController.saveTransaction(getTransactionDTO()).getStatusCode());
  }

  private TransactionDTO getTransactionDTO() {
    TransactionDTO transactionDTO = new TransactionDTO();
    transactionDTO.setTransactionDate(LocalDateTime.now());
    transactionDTO.setAmount(12.00);
    transactionDTO.setCustomerId("123");
    return transactionDTO;
  }
}
