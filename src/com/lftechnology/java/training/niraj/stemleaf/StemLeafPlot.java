package com.lftechnology.java.training.niraj.stemleaf;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lftechnology.java.training.alina.basic.SumOfMultiples;

/**
 * 
 * Plots stem and leaf
 * 
 * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
 *
 */
public class StemLeafPlot {
	private final static Logger LOGGER = Logger.getLogger(SumOfMultiples.class
			.getName());

	private StemLeafPlot() {

	}

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			int dataPointLength;

			LOGGER.log(Level.INFO, "Please provide the number of data points:");
			while (true) {
				while (!scanner.hasNextInt()) {
					LOGGER.warning("Please enter a number:");
					scanner.next();
				}
				dataPointLength = scanner.nextInt();
				if (dataPointLength < 0) {
					LOGGER.warning("Please provide a positive number");
					scanner.next();
				} else {
					break;
				}
			}
			int[] dataPoints = getDataPoints(dataPointLength, scanner);
			StemLeaf stemLeaf = new StemLeaf(dataPoints);
			stemLeaf.plotStemLeaf();

		} catch (InputMismatchException e) {
			LOGGER.warning("Invalid input is provided");
		}

	}

	/**
	 * Get data points related information from the user
	 * 
	 * @param dataPointLength
	 *            {@link Integer}
	 * @param scanner
	 *            {@link Scanner}
	 * @return {@link Integer[]}
	 * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
	 */
	private static int[] getDataPoints(int dataPointLength, Scanner scanner) {
		int[] dataPoints = new int[dataPointLength];
		int dataPoint;
		for (int i = 0; i < dataPointLength; i++) {
			System.out.println("datapoint[" + i + "] :");
			while (true) {
				while (!scanner.hasNextInt()) {
					LOGGER.warning("Please provide the integer value:");
					scanner.next();
				}
				dataPoint = scanner.nextInt();
				if (dataPoint < 0) {
					LOGGER.warning("Please provide positive data points:");
					scanner.next();
				} else {
					break;
				}
			}
			dataPoints[i] = dataPoint;
		}

		return dataPoints;

	}

}
