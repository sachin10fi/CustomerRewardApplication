package com.customers.rewards.service;

import static org.junit.Assert.*;

import com.customers.rewards.dto.CustomerDTO;
import com.customers.rewards.exception.CustomerAlreadyExistsException;
import com.customers.rewards.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

  @Mock private CustomerRepository customerRepository;

  @InjectMocks private CustomerService customerService;

  @Test
  public void testRegisterCustomer() throws CustomerAlreadyExistsException {
    Mockito.when(customerRepository.existsById(Mockito.anyString())).thenReturn(false);
    customerService.registerCustomer(getCustomerDTO());
    Mockito.verify(customerRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test(expected = CustomerAlreadyExistsException.class)
  public void testRegisterCustomerException() throws CustomerAlreadyExistsException {
    Mockito.when(customerRepository.existsById(Mockito.anyString())).thenReturn(true);
    customerService.registerCustomer(getCustomerDTO());
  }

  private CustomerDTO getCustomerDTO() {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setId("123");
    customerDTO.setPassword("ABC!123455a");
    customerDTO.setCustomerName("SAm");
    return customerDTO;
  }
}
