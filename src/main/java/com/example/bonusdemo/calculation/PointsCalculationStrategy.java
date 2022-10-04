package com.example.bonusdemo.calculation;

import com.example.bonusdemo.db.Transaction;

public interface PointsCalculationStrategy {

    double calculate(Transaction transaction);

}
