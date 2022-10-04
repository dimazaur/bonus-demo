package com.example.bonusdemo.service;

import com.example.bonusdemo.db.Transaction;
import com.example.bonusdemo.db.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    public List<Transaction> findAll() {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        return Streamable.of(transactions).toList();
    }

    public List<Transaction> findByUserId(String userId) {
        return transactionRepository.findByUserIdOrderByTimestamp(userId);
    }

    public List<Transaction> findByUserIdDate(String userId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return transactionRepository.findByUserIdAndTimestampBetweenOrderByTimestamp(userId, dateFrom, dateTo);
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void saveAll(List<Transaction> transactionList) {
        transactionRepository.saveAll(transactionList);
    }

}
