package com.customers.rewards.controller;

import com.customers.rewards.dto.TransactionDTO;
import com.customers.rewards.exception.CustomerNotFoundException;
import com.customers.rewards.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

  private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

  @Autowired private TransactionService transactionService;

  @Operation(summary = "Saves Transaction", description = "Saves Transaction made by a Customer")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successfully saved transaction"),
        @ApiResponse(responseCode = "404", description = "Customer Not Found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error Occurred")
      })
  @PostMapping
  public ResponseEntity<?> saveTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
    try {
      logger.info("Saving Transaction for Customer:{}", transactionDTO.getCustomerId());
      transactionService.saveTransaction(transactionDTO);
    } catch (CustomerNotFoundException ex) {
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      logger.error("Some Exception Occurred:{}", ex.getMessage());
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return ResponseEntity.ok("Transaction Saved Successfully");
  }
}
