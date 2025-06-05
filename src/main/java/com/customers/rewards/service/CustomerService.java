package com.customers.rewards.service;

import com.customers.rewards.dto.CustomerDTO;
import com.customers.rewards.exception.CustomerAlreadyExistsException;
import com.customers.rewards.model.Customer;
import com.customers.rewards.repository.CustomerRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

  @Autowired private CustomerRepository customerRepository;

  @CircuitBreaker(name = "registerCustomer", fallbackMethod = "registerCustomerFallback")
  public void registerCustomer(CustomerDTO customerDTO) throws CustomerAlreadyExistsException {
    Customer customer = Customer.getCustomerEntity(customerDTO);
    if (customerRepository.existsById(customer.getId())) {
      logger.error("Customer Id already Exists");
      throw new CustomerAlreadyExistsException("Customer Id already Exists, choose another id");
    }
    logger.info("Saving Customer Details in DB");
    customerRepository.save(customer);
  }

  public void registerCustomerFallback(CustomerDTO customerDTO, Throwable t) {
    logger.error("Inside Fallback of registerCustomer with error:{}", t.getMessage());
  }
}
