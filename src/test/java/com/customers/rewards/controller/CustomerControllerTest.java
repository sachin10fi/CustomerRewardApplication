package com.customers.rewards.controller;

import com.customers.rewards.dto.CustomerDTO;
import com.customers.rewards.exception.CustomerAlreadyExistsException;
import com.customers.rewards.service.CustomerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

  @Mock private CustomerService customerService;

  @InjectMocks private CustomerController customerController;

  @Test
  public void testSaveCustomerSuccess() throws CustomerAlreadyExistsException {
    Mockito.doNothing().when(customerService).registerCustomer(Mockito.any());
    Assert.assertEquals(
        HttpStatus.OK, customerController.saveCustomer(getCustomerDTO()).getStatusCode());
  }

  @Test
  public void testSaveCustomerFailure() throws CustomerAlreadyExistsException {
    Mockito.doThrow(new CustomerAlreadyExistsException("exists"))
        .when(customerService)
        .registerCustomer(Mockito.any());
    Assert.assertEquals(
        HttpStatus.BAD_REQUEST, customerController.saveCustomer(getCustomerDTO()).getStatusCode());
  }

  private CustomerDTO getCustomerDTO() {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setId("123");
    customerDTO.setPassword("ABC!123455a");
    customerDTO.setCustomerName("SAm");
    return customerDTO;
  }
}
