package com.example.bonusdemo.service;

import com.example.bonusdemo.db.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    List<Transaction> findAll();

    List<Transaction> findByUserId(String userId);

    List<Transaction> findByUserIdDate(String userId, LocalDateTime dateFrom, LocalDateTime dateTo);

    void save(Transaction transaction);

    void saveAll(List<Transaction> transactionList);

}
