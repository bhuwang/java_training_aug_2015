package com.lftechnology.java.training.alina.basic.fibonacci;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Each new term in the Fibonacci sequence is generated by adding the previous two terms. By starting with 1 and 2, the first 10 terms will
 * be: 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ... By considering the terms in the Fibonacci sequence whose values do not exceed four million.
 * Find the sum of the even-valued terms.
 *
 * @author Alina Shakya<alinashakya@lftechnology.com>
 */
public class EvenFibonacci {

    private static final Logger LOGGER = Logger.getLogger(EvenFibonacci.class.getName());

    private EvenFibonacci() {

    }

    public static void main(String[] args) throws Exception {
        try {
            SumOfEvenFibonacci fibonacci = new SumOfEvenFibonacci(0, 1);
            LOGGER.log(Level.INFO, "The sum of the even-valued fibonacci terms : {0}", new Object[] { fibonacci.getSumOfEvenFibonacci() });
        } catch (ArithmeticException ex) {
            LOGGER.log(Level.WARNING, "Exception Message : {0}", new Object[] { ex });
        }
    }

}
