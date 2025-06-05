package com.customers.rewards.controller;

import com.customers.rewards.dto.RewardsDTO;
import com.customers.rewards.exception.CustomerNotFoundException;
import com.customers.rewards.service.RewardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rewards")
public class RewardsController {

  private static final Logger logger = LoggerFactory.getLogger(RewardsController.class);

  @Autowired private RewardsService rewardsService;

  @Operation(
      summary = "Fetches Reward Points of a Customer",
      description = "Fetches Reward Points of a Customer based on provided dates")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successfully fetched reward points"),
        @ApiResponse(responseCode = "404", description = "Customer Not Found"),
        @ApiResponse(responseCode = "400", description = "Invalid Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error Occurred")
      })
  @GetMapping("/{customerId}")
  public ResponseEntity<?> getRewardPoints(
      @PathVariable String customerId,
      @RequestParam LocalDate startDate,
      @RequestParam LocalDate endDate) {
    RewardsDTO rewardsDTO = null;
    try {
      logger.info("Fetching Reward Ponts for Customer:{}", customerId);
      rewardsDTO = rewardsService.getTotalRewards(customerId, startDate, endDate);
    } catch (CustomerNotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      logger.error("Some Exception Occurred:{}", e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    logger.info("Successfully Fetched Reward points");
    return ResponseEntity.ok(rewardsDTO);
  }
}
