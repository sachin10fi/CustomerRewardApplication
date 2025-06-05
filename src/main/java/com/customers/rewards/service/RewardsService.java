package com.customers.rewards.service;

import com.customers.rewards.dto.MonthlyRewardDTO;
import com.customers.rewards.dto.RewardsDTO;
import com.customers.rewards.exception.CustomerNotFoundException;
import com.customers.rewards.model.Transaction;
import com.customers.rewards.repository.CustomerRepository;
import com.customers.rewards.repository.TransactionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.time.LocalDate;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardsService {

  private static final Logger logger = LoggerFactory.getLogger(RewardsService.class);

  @Autowired private CustomerRepository customerRepository;

  @Autowired private TransactionRepository transactionRepository;

  @CircuitBreaker(name = "getTotalRewards", fallbackMethod = "getTotalRewardsFallback")
  public RewardsDTO getTotalRewards(String customerId, LocalDate startDate, LocalDate endDate)
      throws CustomerNotFoundException {

    if (!checkCustomer(customerId)) {
      logger.error("Customer doesn't Exist with Customer Id:{}", customerId);
      throw new CustomerNotFoundException("Customer Doesn't Exist");
    }

    if (null == startDate || null == endDate || startDate.isAfter(endDate)) {
      logger.error("Invalid Dates Provided, start date:{} end date:{}", startDate, endDate);
      throw new IllegalArgumentException("Invalid Dates Provided");
    }
    List<Transaction> totalTransactions =
        transactionRepository.findByCustomerIdAndCreatedAtBetween(
            customerId,
            startDate.atStartOfDay(),
            endDate.atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59));

    RewardsDTO rewardsDTO = new RewardsDTO();
    int totalRewardPoints = 0;
    List<MonthlyRewardDTO> monthlyRewardDTOS = new ArrayList<>();

    Map<String, MonthlyRewardDTO> monthlyRewardPointsMap = new HashMap<>();

    for (Transaction transaction : totalTransactions) {
      String mapKey =
          transaction.getTransactionDate().getYear()
              + "-"
              + transaction.getTransactionDate().getMonth();

      int rewardPoints = getRewardPointsPerTransaction(transaction.getAmount());

      MonthlyRewardDTO monthlyRewardDTO = monthlyRewardPointsMap.get(mapKey);

      if (null == monthlyRewardDTO) {
        monthlyRewardDTO = new MonthlyRewardDTO();
        monthlyRewardDTO.setMonth(transaction.getTransactionDate().getMonth().toString());
        monthlyRewardDTO.setRewardPoints(rewardPoints);
        monthlyRewardDTOS.add(monthlyRewardDTO);
      } else {
        monthlyRewardDTO.setRewardPoints(monthlyRewardDTO.getRewardPoints() + rewardPoints);
      }
      monthlyRewardPointsMap.put(mapKey, monthlyRewardDTO);
      totalRewardPoints += rewardPoints;
    }

    logger.info("Monthly Reward Points:{}", monthlyRewardPointsMap);
    logger.info("Total Reward points:{}", totalRewardPoints);

    rewardsDTO.setTotalRewardPoints(totalRewardPoints);
    rewardsDTO.setMonthlyRewardDTOS(monthlyRewardDTOS);

    return rewardsDTO;
  }

  public RewardsDTO getTotalRewardsFallback(
      String customerId, LocalDate startDate, LocalDate endDate, Throwable t) {
    logger.error(
        "Inside Fallback for getTotalRewards for Customer:{} with error:{}",
        customerId,
        t.getMessage());
    return new RewardsDTO();
  }

  // Checking for valid customer
  private boolean checkCustomer(String customerId) {
    return customerRepository.existsById(customerId);
  }

  // Checking points based on the logic
  private int getRewardPointsPerTransaction(double amount) {
    int points = 0;
    if (amount > 100) {
      points += (int) ((amount - 100) * 2);
      points += 50;
    } else if (amount > 50) {
      points += (int) (amount - 50);
    }
    return points;
  }
}
