package com.example.bonusdemo.service;

import com.example.bonusdemo.db.Transaction;
import com.example.bonusdemo.db.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    public List<Transaction> findAll() {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        return Streamable.of(transactions).toList();
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

}
