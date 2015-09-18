package com.lftechnology.java.training.alina.jdbc.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lftechnology.java.training.alina.jdbc.constants.Constants;
import com.lftechnology.java.training.alina.jdbc.controller.EmployeeController;
import com.lftechnology.java.training.alina.jdbc.controller.LoginController;
import com.lftechnology.java.training.alina.jdbc.dao.employee.impl.EmployeeDaoImpl;
import com.lftechnology.java.training.alina.jdbc.dao.user.impl.UserDaoImpl;
import com.lftechnology.java.training.alina.jdbc.domain.Database;
import com.lftechnology.java.training.alina.jdbc.domain.Employee;
import com.lftechnology.java.training.alina.jdbc.domain.User;
import com.lftechnology.java.training.alina.jdbc.employeeenum.EmployeeRole;

/**
 * UserService consists of all functionalities of user
 * 
 * @author Alina Shakya <alinashakya@lftechnology.com>
 */
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private static UserDaoImpl userDao = new UserDaoImpl();
    private static EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

    /**
     * Checks for user login
     * 
     * @param scanner
     *            {@link Scanner}
     * @param user
     *            {@link User}
     * @return isLogin {@link Boolean} true if login success, false if login fails
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public Boolean checkEmployeeLogin(Scanner scanner, User user) throws SQLException {
        return userDao.checkEmployeeLogin(scanner, user);
    }

    /**
     * Gets login info
     * 
     * @param scanner
     *            {@link Scanner}
     * @return user {@link User} user info
     * @author Alina Shakya <alinashakya@lftechnology.com>
     * @throws SQLException
     */
    public static User setLoginInfo(Scanner scanner, String actionType) throws SQLException {
        User user = new User();
        boolean userExist = false;
        if (actionType == Constants.USER_LOGIN) {
            user.setUsername(UtilityService.getInputData(scanner, Constants.ENTER_USERNAME));
        } else {
            do {
                String username = UtilityService.getInputData(scanner, Constants.ENTER_USERNAME);
                userExist = checkExistUsername(username);
                if (!userExist) {
                    user.setUsername(username);
                }
            } while (userExist);
        }
        user.setPassword(UtilityService.getInputData(scanner, Constants.ENTER_PASSWORD));
        user.setTerminated(true);
        user.setCreatedAt(DateTimeService.getCurrentTimeStamp());
        user.setModifiedAt(DateTimeService.getCurrentTimeStamp());
        return user;
    }

    /**
     * Checks existence of user by username
     * 
     * @param scanner
     *            {@link Scanner}
     * @param userExist
     *            {@link Boolean}
     * @param username
     *            {@link String}
     * @return userExist {@link Boolean} identifies user exists/not
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public static boolean checkExistUsername(String username) throws SQLException {
        boolean checkUserExist = userDao.checkValidUserByUsername(username);
        if (checkUserExist) {
            LOGGER.log(Level.INFO, Constants.USERNAME_ALREADY_EXISTS);
        }
        return checkUserExist;
    }

    /**
     * Used to get Role of an employee
     * 
     * @param scanner
     *            {@link Scanner}
     * @param result
     *            {@link ResultSet}
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public void getEmployeeRole(Scanner scanner, Employee employee) throws SQLException {
        if (employee.getRole().equals(EmployeeRole.ADMIN.getRole())) {
            LoginController.getAdminRole(scanner, employee);
        } else {
            LoginController.getNormalUserRole(scanner, employee);
        }
    }

    /**
     * Gets employee info
     * 
     * @param scanner
     *            {@link Scanner}
     * @param userId
     *            {@link Integer}
     * @return employee {@link Employee} employee info
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public static Employee setEmployeeInfo(Scanner scanner, int userId) {
        Employee employee = new Employee();
        employee.setFullname(UtilityService.getInputData(scanner, Constants.ENTER_FULLNAME));
        employee.setDepartment(UtilityService.getInputData(scanner, Constants.ENTER_DEPARTMENT));
        employee.setAddress(UtilityService.getInputData(scanner, Constants.ENTER_ADDRESS));
        Boolean roleStatus = false;
        do {
            String role = UtilityService.getInputData(scanner, Constants.ENTER_ROLE);
            roleStatus = EmployeeController.checkMatchedRole(roleStatus, role);
            if (roleStatus) {
                employee.setRole(role);
            }
        } while (!roleStatus);
        employee.setUserId(userId);
        employee.setCreatedAt(DateTimeService.getCurrentTimeStamp());
        employee.setModifiedAt(DateTimeService.getCurrentTimeStamp());
        return employee;
    }

    /**
     * Sets employee data
     * 
     * @param result
     *            {@link ResultSet}
     * @return employee {@link Employee} employee details
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public Employee map(ResultSet result) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(result.getInt("employee_id"));
        employee.setUserId(result.getInt("user_id"));
        employee.setUsername(result.getString("username"));
        employee.setTerminated(result.getBoolean("is_terminated"));
        employee.setCreatedAt(result.getTimestamp("created_at"));
        employee.setFullname(result.getString("fullname"));
        employee.setDepartment(result.getString("department"));
        employee.setRole(result.getString("role"));
        employee.setAddress(result.getString("address"));
        employee.setCreatedAt(result.getTimestamp("created_at"));
        return employee;
    }

    /**
     * Sets database params
     * 
     * @param params
     *            {@link Map}
     * @return database {@link Database} database parameters
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public static Database setDatabaseParams(Map<Integer, Object> params) {
        Database database = new Database();
        database.setParameters(params);
        return database;
    }

    /**
     * Adds login info
     * 
     * @param scanner
     *            {@link Scanner}
     * @return user {@link User} user details
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public static User addLoginInfo(Scanner scanner) throws SQLException {
        User user = new User();
        user = UserService.setLoginInfo(scanner, Constants.USER_ADD);
        userDao.addNew(user);
        return user;
    }

    /**
     * Add employee info
     * 
     * @param scanner
     *            {@link Scanner}
     * @param userId
     *            {@link Integer}
     * @return employee {@link Employee} employee details
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public static Employee addEmployeeInfo(Scanner scanner, int userId) throws SQLException {
        Employee employee = new Employee();
        employee = UserService.setEmployeeInfo(scanner, userId);
        employeeDao.addNew(employee);
        return employee;
    }

    /**
     * Deletes employee
     * 
     * @param employee
     *            {@link Employee}
     * @return {@link Boolean}
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public static boolean deleteEmployeeByFullname(Employee employee) throws SQLException {
        return employeeDao.delete(employee.getFullname());
    }

    /**
     * Terminates employee
     * 
     * @param employee
     *            {@link Employee}
     * @return {@link Boolean}
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public static boolean terminateEmployeeByFullName(Employee employee) throws SQLException {
        return userDao.delete(employee.getFullname());
    }

    /**
     * Gets employee list
     * 
     * @return list {@link List}
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public static List<Employee> employeeList() throws SQLException {
        List<Employee> list = employeeDao.findAll();
        Collections.sort(list);
        return list;
    }

    /**
     * Searches employee
     * 
     * @param scanner
     *            {@link Scanner}
     * @return list {@link List} user list
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public static List<Employee> searchEmployee(Scanner scanner) throws SQLException {
        String searchContent = UtilityService.getInputData(scanner, Constants.SEARCH_EMPLOYEE_CRITERIA);
        List<Employee> list = employeeDao.searchEmployee(searchContent, searchContent, searchContent);
        Collections.sort(list);
        return list;
    }

    /**
     * Updates employee info
     * 
     * @param params
     *            {@link Map}
     * @param sqlQuery
     *            {@link String}
     * @return result {@link Integer}
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    public static int updateEmployeeInfo(Map<Integer, Object> params, String sqlQuery) throws SQLException {
        Database database = new Database();
        database.setParameters(params);
        database.setSqlQuery(sqlQuery);
        return employeeDao.update(database);
    }
}
