package com.example.bonusdemo.service;

import com.example.bonusdemo.db.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> findAll();

    void save(Transaction transaction);

}
