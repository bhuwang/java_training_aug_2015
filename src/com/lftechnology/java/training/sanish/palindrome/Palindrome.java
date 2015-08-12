package com.lftechnology.java.training.sanish.palindrome;

import java.util.logging.Logger;

/**
 * Provide palindrome calculation functions
 * 
 * @author Sanish Maharjan <sanishmaharjan@lftechnology.com>
 */
public class Palindrome {
	/**
	 * Calculate and return largest palindrome number made from the product of
	 * two provided digit numbers
	 * 
	 * @param digitLength
	 *            {@link Integer} digit length of two product from which have
	 *            find largest palindrome number
	 * @return {@link Integer} largest palindrome number
	 * @throws Exception
	 *             Digit length greater than 4 not supported
	 * @author Sanish Maharjan <sanishmaharjan@lftechnology.com>
	 */
	public static long getLargestPalindrome(int digitLength) throws Exception {
		final Logger LOGGER = Logger.getLogger(Main.class.getName());
		int maxNum = (int) Math.pow(10, digitLength) - 1;
		int minNum = (int) Math.pow(10, digitLength - 1);
		long multipleNum;
		for (long i = maxNum; i > minNum; i--) {
			for (long j = maxNum; j > minNum; j--) {
				multipleNum = (long) j * i;
				if (isPalindromNum(multipleNum)) {
					LOGGER.info(i + " * " + j + " = " + multipleNum);
					return multipleNum;
				}
			}
		}

		return 0;
	}

	/**
	 * Check is supplied number is palindrom number or not
	 * 
	 * @param number
	 *            {@link Integer} number that have to check is palindrom number
	 * @return {@link Boolean} true if supplied number is palindrom else false
	 * @author Sanish Maharjan <sanishmaharjan@lftechnology.com>
	 */
	public static boolean isPalindromNum(long number) {
		long preNumber = number;
		long reverse = 0;
		while (number != 0) {
			reverse = reverse * 10;
			reverse += number % 10;
			number = number / 10;
		}

		return preNumber == reverse;
	}
}
