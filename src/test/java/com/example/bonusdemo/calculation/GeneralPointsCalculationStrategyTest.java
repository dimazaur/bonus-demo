package com.example.bonusdemo.calculation;

import com.example.bonusdemo.db.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralPointsCalculationStrategyTest {

    private final GeneralPointsCalculationStrategy generalPointsCalculationStrategy = new GeneralPointsCalculationStrategy();

    @Test
    public void testZero() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.ZERO);

        double points = generalPointsCalculationStrategy.calculate(transaction);
        assertEquals(0.0, points);
    }

    @Test
    public void testNegative() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(-100.0));

        double points = generalPointsCalculationStrategy.calculate(transaction);
        assertEquals(0.0, points);
    }

    @Test
    public void testNoBonus() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(40.0));

        double points = generalPointsCalculationStrategy.calculate(transaction);
        assertEquals(0.0, points);
    }

    @Test
    public void testExactFirstLevel() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(50.0));

        double points = generalPointsCalculationStrategy.calculate(transaction);
        assertEquals(0.0, points);
    }

    @Test
    public void testFirstLevelBonus() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(75.0));

        double points = generalPointsCalculationStrategy.calculate(transaction);
        assertEquals(25.0, points);
    }

    @Test
    public void testFirstLevelCents() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(75.55));

        double points = generalPointsCalculationStrategy.calculate(transaction);
        assertEquals(25.0, points);
    }

    @Test
    public void testExactSecondLevel() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100.0));

        double points = generalPointsCalculationStrategy.calculate(transaction);
        assertEquals(50.0, points);
    }

    @Test
    public void testSecondLevelBonus() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(120.0));

        double points = generalPointsCalculationStrategy.calculate(transaction);
        assertEquals(90.0, points);
    }

    @Test
    public void testSecondLevelBonusCents() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(120.99));

        double points = generalPointsCalculationStrategy.calculate(transaction);
        assertEquals(90.0, points);
    }

    @Test
    public void testBigAmountBonus() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(1_000_000.0));

        double points = generalPointsCalculationStrategy.calculate(transaction);
        assertEquals(1999850.0, points);
    }


}
