package com.example.bonusdemo.db;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByUserIdOrderByTimestamp(String userId);

    List<Transaction> findByUserIdAndTimestampBetweenOrderByTimestamp(String userId, LocalDateTime dateFrom, LocalDateTime dateTo);

}
