package com.example.bonusdemo.calculation;

import com.example.bonusdemo.db.Transaction;

import java.math.BigDecimal;

public class GeneralPointsCalculationStrategy implements PointsCalculationStrategy {

    private static final double FIRST_LEVEL_POINT = 1;
    private static final double FIRST_LEVEL = 50.0;
    private static final double SECOND_LEVEL_POINT = 2;
    private static final double SECOND_LEVEL = 100.0;

    @Override
    public double calculate(Transaction transaction) {
        double points = 0;
        BigDecimal amount = transaction.getAmount();

        if (SECOND_LEVEL < amount.doubleValue()) {
            points += Math.floor(amount.doubleValue() - SECOND_LEVEL) * SECOND_LEVEL_POINT;
        }

        if (FIRST_LEVEL < amount.doubleValue()) {
            points += Math.floor(Math.min(amount.doubleValue(), SECOND_LEVEL) - FIRST_LEVEL) * FIRST_LEVEL_POINT;
        }

        return points;
    }
}
