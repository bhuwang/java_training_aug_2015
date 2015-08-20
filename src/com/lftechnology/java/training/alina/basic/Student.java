package com.lftechnology.java.training.alina.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Student Class consists of all the details of student's & their performance
 * 
 * @author Alina Shakya <alinashakya@lftechnology.com>
 */
public class Student extends Course implements Comparable<Course> {

	static ArrayList<Student> students = new ArrayList<Student>();
	static ArrayList<Course> courses = new ArrayList<Course>();

	private static final Logger LOGGER = Logger.getLogger(Student.class.getName());
	private String fullName;
	private int standard;
	private int rollNo;

	public Student(float subject1, float subject2, float subject3,
			float subject4, float subject5, String fullName, int standard,
			int rollNo) {
		super(subject1, subject2, subject3, subject4, subject5);
		this.fullName = fullName;
		this.standard = standard;
		this.rollNo = rollNo;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setStandard(int standard) {
		this.standard = standard;
	}

	public int getStandard() {
		return standard;
	}

	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}

	public int getRollNo() {
		return rollNo;
	}

	/**
	 * Function used to get all the details regarding students performance
	 * Displays students details with their marks
	 * 
	 * @param input
	 *            {@link Scanner}
	 * @param studentNo
	 *            {@link Integer} number of students
	 * @author Alina Shakya <alinashakya@lftechnology.com>
	 */
	public static void getStudentDetails(Scanner input, int studentNo) {
		for (int i = 0; i < studentNo; i++) {
			LOGGER.log(Level.INFO, "Enter Student's Full Name : ");
			String fullName = input.nextLine();
			MarksValidation validation = new MarksValidation();
			int standard = validation.numberValidation(input, "Enter Standard : ");
			int rollNumber = validation.numberValidation(input, "Enter Roll Number : ");
			LOGGER.log(Level.INFO, "Enter Marks for following Subjects : ");
			float maths = validation.marksValidation(input, "Maths : ");
			float science = validation.marksValidation(input, "Science : ");
			float english = validation.marksValidation(input, "English : ");
			float nepali = validation.marksValidation(input, "Nepali : ");
			float history = validation.marksValidation(input, "History : ");
			Student student = new Student(maths, science, english, nepali,
					history, fullName, standard, rollNumber);
			students.add(student);
		}
		displayStudentDetails(students, courses);
	}

	/**
	 * Function used to display all the student performance details
	 * 
	 * @param students
	 *            {@link ArrayList} arrays of student details
	 * @param courses
	 *            {@link ArrayList} arrays of course details
	 * @author Alina Shakya <alinashakya@lftechnology.com>
	 */
	private static void displayStudentDetails(ArrayList<Student> students,
			ArrayList<Course> courses) {
		Comparator<Student> mycomparator = Collections.reverseOrder();
		Collections.sort(students, mycomparator);
		for (Student student : students) {
			LOGGER.log(Level.INFO, "Student lists : {0}", new Object[] { student });
		}
	}

	public String toString() {
		return "Name : " + this.fullName + ", Class : " + this.standard
				+ ", Roll Number : " + this.rollNo + "," + super.toString();
	}

	public int compareTo(Course val) {
		int percentageToCompare = (int) val.getPercentage();
		return (int) (this.getPercentage() - percentageToCompare);
	}

}
