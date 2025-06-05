package com.customers.rewards.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
public class RewardsDTO {

  Integer totalRewardPoints;

  List<MonthlyRewardDTO> monthlyRewardDTOS;
}
