package com.example.bonusdemo.controller;

import com.example.bonusdemo.calculation.PointsCalculationStrategy;
import com.example.bonusdemo.db.Transaction;
import com.example.bonusdemo.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class PointController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PointsCalculationStrategy pointsCalculationStrategy;

    @GetMapping(path = "/points/total/{userId}")
    public PointDto pointsTotal(@PathVariable String userId) {
        log.debug("Calculate total points for userId: {}", userId);
        List<Transaction> transactions = transactionService.findByUserId(userId);
        log.debug("Transactions: {}", transactions);

        return calculatePoints(transactions);
    }

    @GetMapping(path = "/points/{userId}")
    public PointDto pointsForPeriod(@PathVariable String userId,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateFrom,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTo) {

        log.debug("Calculate points for userId: {}, dataFrom: {}, dateTo: {}", userId, dateFrom, dateTo);
        List<Transaction> transactions = transactionService.findByUserIdDate(userId, dateFrom, dateTo);
        log.debug("Transactions: {}", transactions);

        return calculatePoints(transactions);
    }

    private PointDto calculatePoints(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transactions not found");
        }

        double points = 0.0;
        for (Transaction transaction : transactions) {
            points += pointsCalculationStrategy.calculate(transaction);
        }

        return new PointDto(points);
    }

}
