package com.customers.rewards.model;

import com.customers.rewards.dto.CustomerDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

  @Id private String id;

  private String password;

  private String customerName;

  public static Customer getCustomerEntity(CustomerDTO customerDTO) {
    Customer customer = new Customer();
    customer.setId(customerDTO.getId());
    customer.setPassword(customerDTO.getPassword());
    customer.setCustomerName(customerDTO.getCustomerName());
    return customer;
  }
}
