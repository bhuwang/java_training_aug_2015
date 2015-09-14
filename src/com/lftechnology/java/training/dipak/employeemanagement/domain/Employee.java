package com.lftechnology.java.training.dipak.employeemanagement.domain;

public class Employee extends User{
	private String fullName;
	private String department;
	private String address;
	private UserType role;
	public String getFullName() {
		
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDepartment() {

		return department;
	}

	public void setDepartment(String department) {

		this.department = department;
	}

	public String getAddress() {

		return address;
	}

	public void setAddress(String address) {

		this.address = address;
	}

	public UserType getRole() {

		return role;
	}

	public void setRole(UserType role) {

		this.role = role;
	}
	
}
