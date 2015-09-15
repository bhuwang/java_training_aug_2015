package com.lftechnology.java.training.niraj.ems.domains;

import java.util.List;
import java.util.Map;

import com.lftechnology.java.training.niraj.ems.enums.Roles;

public class Employee extends User {

    private String fullname;
    private String department;
    private String address;
    private String role;

    private List<String> attributeList;

    public Employee() {
        super();
        setFullname(null);
        setDepartment(null);
        setAddress(null);
        setRole(Roles.USER);
        this.setAttributeList();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role.getRole();
    }

    public List<String> getAttributeList() {
        return attributeList;
    }

    private void setAttributeList() {
        this.attributeList = super.getAttributeList();
        this.attributeList.add("fullname");
        this.attributeList.add("department");
        this.attributeList.add("address");
        this.attributeList.add("role");

    }

    @Override
    public List<String> getAttributes() {
        return getAttributeList();
    }

    @Override
    public Map<String, String> getInfo() {
        Map<String, String> employeeInfo = super.getInfo();
        employeeInfo.put("fullname", getFullname());
        employeeInfo.put("department", getDepartment());
        employeeInfo.put("address", getAddress());
        employeeInfo.put("role", getRole());
        return employeeInfo;
    }

    @Override
    public String toString() {
        return "Employee [fullname=" + fullname + ", department=" + department + ", address=" + address + ", role=" + role + ", username="
                + username + ", isTerminated=" + isTerminated + ", status=" + status + "]";
    }

}
