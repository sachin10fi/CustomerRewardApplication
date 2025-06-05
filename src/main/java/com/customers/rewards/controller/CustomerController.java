package com.customers.rewards.controller;

import com.customers.rewards.dto.CustomerDTO;
import com.customers.rewards.exception.CustomerAlreadyExistsException;
import com.customers.rewards.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

  @Autowired private CustomerService customerService;

  @Operation(
      summary = "Register a new customer",
      description = "Registers a new customer using ID, name, and Password")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successfully registered"),
        @ApiResponse(
            responseCode = "400",
            description = "Customer ID already exists, Invalid Request")
      })
  @PostMapping("/register")
  public ResponseEntity<?> saveCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
    try {
      logger.info("Registering Customer with Customer Id:{}", customerDTO.getId());
      customerService.registerCustomer(customerDTO);
    } catch (CustomerAlreadyExistsException ex) {
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    logger.info("Successfully Registered Customer");
    return ResponseEntity.ok("Successfully Registered Customer");
  }
}
