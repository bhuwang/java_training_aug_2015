package com.lftechnology.java.training.alina.jdbc.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lftechnology.java.training.alina.jdbc.constants.Constants;
import com.lftechnology.java.training.alina.jdbc.controller.EmployeeController;
import com.lftechnology.java.training.alina.jdbc.controller.LoginController;
import com.lftechnology.java.training.alina.jdbc.dao.user.impl.UserDaoImpl;
import com.lftechnology.java.training.alina.jdbc.domain.Database;
import com.lftechnology.java.training.alina.jdbc.domain.Employee;
import com.lftechnology.java.training.alina.jdbc.domain.User;
import com.lftechnology.java.training.alina.jdbc.views.AdminView;
import com.lftechnology.java.training.alina.jdbc.views.EmployeeEditView;
import com.lftechnology.java.training.alina.jdbc.views.EmployeeView;

/**
 * UserService consists of all functionalities of user
 * 
 * @author Alina Shakya <alinashakya@lftechnology.com>
 */
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

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
            user.setUsername(UtilityService.getInputData(scanner, "Enter Username : "));
        } else {
            do {
                String username = UtilityService.getInputData(scanner, "Enter Username : ");
                userExist = LoginController.checkExistUsername(scanner, userExist, username);
                if (!userExist) {
                    user.setUsername(username);
                }
            } while (userExist);
        }
        user.setPassword(UtilityService.getInputData(scanner, "Enter Password : "));
        user.setTerminated(true);
        user.setCreatedAt(DateTimeService.getCurrentTimeStamp());
        user.setModifiedAt(DateTimeService.getCurrentTimeStamp());
        return user;
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
    public void getEmployeeRole(Scanner scanner, ResultSet result) throws SQLException {
        if (result.getString("role").equals(Employee.EmployeeRole.ADMIN.role)) {
            getAdminRole(scanner, result);
        } else {
            getNormalUserRole(scanner, result);
        }
    }

    /**
     * Gets Admin role functionalities
     * 
     * @param scanner
     *            {@link Scanner}
     * @param result
     *            {@link ResultSet}
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    private void getAdminRole(Scanner scanner, ResultSet result) throws SQLException {
        char choice = ' ';
        while (choice != Constants.ADMIN_EXIT) {
            AdminView.displayAdminRoleMenu(result);
            choice = UtilityService.getSelectedMenu(scanner, "Select an option (a-f) : ");
            getAdminRoleOptions(scanner, choice);
        }
    }

    /**
     * Gets option menu for Admin Role
     * 
     * @param scanner
     *            {@link Scanner}
     * @param option
     *            {@link Character}
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    private void getAdminRoleOptions(Scanner scanner, char option) throws SQLException {
        switch (option) {
        case 'a':
            LOGGER.log(Level.INFO, "\n========================\nAdd New Employee : \n========================\n");
            EmployeeController.addNewEmployee(scanner);
            break;
        case 'b':
            LOGGER.log(Level.INFO, "\n========================\nDelete an Employee : \n========================\n");
            EmployeeController.deleteExistingEmployee(scanner);
            return;
        case 'c':
            LOGGER.log(Level.INFO, "\n========================\nTerminate an Employee : \n========================\n");
            EmployeeController.terminateExistingEmployee(scanner);
            return;
        case 'd':
            LOGGER.log(Level.INFO, "\n========================\nView Employee List : \n========================\n");
            EmployeeController.getEmployeeList();
            return;
        case 'e':
            LOGGER.log(Level.INFO, "\n========================\nSearch an Employee : \n========================\n");
            EmployeeController.searchExistingEmployee(scanner);
            return;
        case 'f':
            LOGGER.log(Level.INFO, "\n========================\nUser successfully logged out.\n========================\n");
            UserDaoImpl userDao = new UserDaoImpl();
            userDao.checkEmployeeLogin(scanner);
        default:
            LOGGER.log(Level.INFO, "\n========================\nInvalid entry, Please choose from menu option.\n========================\n");
            return;
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
        employee.setFullname(UtilityService.getInputData(scanner, "Enter Fullname : "));
        employee.setDepartment(UtilityService.getInputData(scanner, "Enter department : "));
        employee.setAddress(UtilityService.getInputData(scanner, "Enter address : "));
        Boolean roleStatus = false;
        do {
            String role = UtilityService.getInputData(scanner, "Enter role (user/admin): ");
            roleStatus = EmployeeController.checkMatchedRole(scanner, roleStatus, role);
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
     * Gets role of normal user
     * 
     * @param scanner
     *            {@link Scanner}
     * @param result
     *            {@link ResultSet}
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    private void getNormalUserRole(Scanner scanner, ResultSet result) throws SQLException {
        char choice = ' ';
        while (choice != Constants.EMPLOYEE_EXIT) {
            EmployeeView.displayEmployeeRoleMenu(result);
            choice = UtilityService.getSelectedMenu(scanner, "Select an option (a-d) : ");
            getEmployeeRoleOptions(scanner, choice, result);
        }
    }

    /**
     * Gets employee role menu options
     * 
     * @param scanner
     *            {@link Scanner}
     * @param choice
     *            {@link Character}
     * @author Alina Shakya <alinashakya@lftechnology.com>
     * @throws SQLException
     */
    private void getEmployeeRoleOptions(Scanner scanner, char choice, ResultSet result) throws SQLException {
        switch (choice) {
        case 'a':
            LOGGER.log(Level.INFO, "\n========================\nView Employee List : \n========================\n");
            EmployeeController.getEmployeeList();
            break;
        case 'b':
            LOGGER.log(Level.INFO, "\n========================\nSearch an Employee : \n========================\n");
            EmployeeController.searchExistingEmployee(scanner);
            return;
        case 'c':
            LOGGER.log(Level.INFO, "\n========================\nEdit Information\n========================\n");
            getEditInfo(scanner, result);
        case 'd':
            LOGGER.log(Level.INFO, "\n========================\nUser successfully logged out.\n========================\n");
            UserDaoImpl userDao = new UserDaoImpl();
            userDao.checkEmployeeLogin(scanner);
        default:
            LOGGER.log(Level.INFO, "\n========================\nInvalid entry, Please choose from menu option.\n========================\n");
            return;
        }
    }

    /**
     * Gets employee edit informations
     * 
     * @param scanner
     *            {@link Scanner}
     * @param result
     *            {@link ResultSet}
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    private void getEditInfo(Scanner scanner, ResultSet result) throws SQLException {
        char choice = ' ';
        while (choice != Constants.EMPLOYEE_EDIT_EXIT) {
            EmployeeEditView.displayEmployeeEditMenu();
            choice = UtilityService.getSelectedMenu(scanner, "Select an option (a-e) : ");
            getEmployeeEditOptions(scanner, choice, result);
        }
    }

    /**
     * Gets employee edit options
     * 
     * @param scanner
     *            {@link Scanner}
     * @param choice
     *            {@link Character}
     * @param result
     *            {@link ResultSet}
     * @throws SQLException
     * @author Alina Shakya <alinashakya@lftechnology.com>
     */
    private void getEmployeeEditOptions(Scanner scanner, char choice, ResultSet result) throws SQLException {
        Employee employee = new Employee();
        switch (choice) {
        case 'a':
            LOGGER.log(Level.INFO, "\n========================\nEdit fullname\n========================\n");
            employee.setFullname(UtilityService.getInputData(scanner, "Enter Fullname to Edit : "));
            EmployeeController.updateEmployeeInfo(Constants.FULLNAME, employee.getFullname(), result.getInt("employee_id"));
            return;
        case 'b':
            LOGGER.log(Level.INFO, "\n========================\nEdit Department\n========================\n");
            employee.setDepartment(UtilityService.getInputData(scanner, "Enter Department to Edit : "));
            EmployeeController.updateEmployeeInfo(Constants.DEPARTMENT, employee.getDepartment(), result.getInt("employee_id"));
            return;
        case 'c':
            LOGGER.log(Level.INFO, "\n========================\nEdit Address\n========================\n");
            employee.setAddress(UtilityService.getInputData(scanner, "Enter Address to Edit : "));
            EmployeeController.updateEmployeeInfo(Constants.ADDRESS, employee.getAddress(), result.getInt("employee_id"));
            return;
        case 'd':
            LOGGER.log(Level.INFO, "\n========================\nChange Password\n========================\n");
            return;
        case 'e':
            LOGGER.log(Level.INFO, "\n========================\nBack\n========================\n");
            getNormalUserRole(scanner, result);
            return;
        default:
            LOGGER.log(Level.INFO, "\n========================\nInvalid entry, Please choose from menu option.\n========================\n");
            return;
        }
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
}
