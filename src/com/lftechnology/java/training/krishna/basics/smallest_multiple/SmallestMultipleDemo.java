package com.lftechnology.java.training.krishna.basics.smallest_multiple;

import java.util.logging.Logger;

public class SmallestMultipleDemo {
	private final static Logger LOGGER = Logger.getLogger(SmallestMultiple.class.getName());

	public static void main(String[] args) {
        int number = 2520;
		SmallestMultiple smallestMultiple = new SmallestMultiple();
		long positiveNumber = smallestMultiple.smallestPositiveNumber(number);
		LOGGER.info("smallest positive number : " + positiveNumber);

	}

}