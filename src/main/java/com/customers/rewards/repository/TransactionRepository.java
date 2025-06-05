package com.customers.rewards.repository;

import com.customers.rewards.model.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @Query(
      "SELECT t FROM Transaction t WHERE t.customerId=:id AND t.transactionDate BETWEEN :start AND :end")
  List<Transaction> findByCustomerIdAndCreatedAtBetween(
      @Param("id") String id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
