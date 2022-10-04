package com.example.bonusdemo.db;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Transaction findByUserId(String userId);

}
