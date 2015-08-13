/**
 * 
 */
package com.lftechnology.java.training.niraj.multiples;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Find the sum of all the multiples of two numbers below certain number
 * 
 * @author Niraj Rajbhandari
 *
 */
public class Multiples {

	static final Logger LOGGER = Logger.getLogger(Multiples.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int sumOfMultiples = getSumOfMultiples(3, 5, 1000);
		LOGGER.log(Level.INFO,
				"The sum of multiples of {0} and {1} below {2} is {3} ",
				new Object[] { 3, 5, 1000, sumOfMultiples });
	}

	/**
	 * Get the sum of multiples of two numbers
	 * 
	 * @param num1 {@link Integer}
	 * @param num2 {@link Integer}
	 * @param maxNum {@link Integer}
	 * @return sum of multiples of two numbers
	 * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
	 */
	private static int getSumOfMultiples(int num1, int num2, int maxNum) {
		int sum = 0;
		for (int i = 1; i < maxNum; i++) {
			if (i % num1 == 0 || i % num2 == 0) {
				sum += i;
			}
		}
		return sum;
	}

}
