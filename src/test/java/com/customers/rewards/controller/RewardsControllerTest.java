package com.customers.rewards.controller;

import com.customers.rewards.dto.RewardsDTO;
import com.customers.rewards.exception.CustomerNotFoundException;
import com.customers.rewards.service.RewardsService;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

@RunWith(MockitoJUnitRunner.class)
public class RewardsControllerTest {

  @Mock private RewardsService rewardsService;

  @InjectMocks private RewardsController rewardsController;

  @Test
  public void testGetRewardPointsSuccess() throws CustomerNotFoundException {
    Mockito.when(rewardsService.getTotalRewards(Mockito.anyString(), Mockito.any(), Mockito.any()))
        .thenReturn(getRewardsDTO());
    Assert.assertEquals(
        HttpStatus.OK,
        rewardsController.getRewardPoints("123", LocalDate.MIN, LocalDate.now()).getStatusCode());
  }

  @Test
  public void testGetRewardPointsCustomerNotFoundException() throws CustomerNotFoundException {
    Mockito.doThrow(new CustomerNotFoundException("msg"))
        .when(rewardsService)
        .getTotalRewards(Mockito.anyString(), Mockito.any(), Mockito.any());
    Assert.assertEquals(
        HttpStatus.NOT_FOUND,
        rewardsController.getRewardPoints("123", LocalDate.MIN, LocalDate.now()).getStatusCode());
  }

  @Test
  public void testGetRewardPointsIllegalArgumentException() throws CustomerNotFoundException {
    Mockito.doThrow(new IllegalArgumentException("msg"))
        .when(rewardsService)
        .getTotalRewards(Mockito.anyString(), Mockito.any(), Mockito.any());
    Assert.assertEquals(
        HttpStatus.BAD_REQUEST,
        rewardsController.getRewardPoints("123", LocalDate.MIN, LocalDate.now()).getStatusCode());
  }

  @Test
  public void testGetRewardPointsException() throws CustomerNotFoundException {
    Mockito.doThrow(new NullPointerException("msg"))
        .when(rewardsService)
        .getTotalRewards(Mockito.anyString(), Mockito.any(), Mockito.any());
    Assert.assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR,
        rewardsController.getRewardPoints("123", LocalDate.MIN, LocalDate.now()).getStatusCode());
  }

  private RewardsDTO getRewardsDTO() {
    RewardsDTO rewardsDTO = new RewardsDTO();
    rewardsDTO.setTotalRewardPoints(12);
    rewardsDTO.setMonthlyRewardDTOS(new ArrayList<>());
    return rewardsDTO;
  }
}
