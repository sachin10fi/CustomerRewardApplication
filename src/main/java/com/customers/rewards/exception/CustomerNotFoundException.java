package com.customers.rewards.exception;

public class CustomerNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  public CustomerNotFoundException(String message) {
    super(message);
  }
}
