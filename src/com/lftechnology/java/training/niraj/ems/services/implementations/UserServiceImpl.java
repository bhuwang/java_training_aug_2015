package com.lftechnology.java.training.niraj.ems.services.implementations;

import java.io.Console;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import com.lftechnology.java.training.niraj.ems.domains.Employee;
import com.lftechnology.java.training.niraj.ems.enums.Operators;
import com.lftechnology.java.training.niraj.ems.enums.Roles;
import com.lftechnology.java.training.niraj.ems.enums.Status;
import com.lftechnology.java.training.niraj.ems.exceptions.CustomException;
import com.lftechnology.java.training.niraj.ems.services.CrudService;
import com.lftechnology.java.training.niraj.ems.utils.Constants;
import com.lftechnology.java.training.niraj.ems.utils.UserNumInputImpl;
import com.lftechnology.java.training.niraj.ems.utils.UserStringInputImpl;
import com.lftechnology.java.training.niraj.ems.utils.Utils;

public class UserServiceImpl implements CrudService<Employee, String> {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    private EmployeeDaoImpl employeeDao;
    private UserStringInputImpl userInput;
    private UserNumInputImpl userNumInput;

    public UserServiceImpl() {
        employeeDao = new EmployeeDaoImpl();
        userInput = new UserStringInputImpl();
        userNumInput = new UserNumInputImpl();
    }

    @Override
    public Employee save(Employee t) throws SQLException {
        return employeeDao.save(t);
    }

    @Override
    public Employee find(String s) throws SQLException {
        return employeeDao.find(s);
    }

    @Override
    public boolean update(Employee t) throws SQLException {
        return employeeDao.update(t);

    }

    /**
     * Logs a user in
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public Employee login(String username, String password) throws SQLException {
        Map<String, String> credentials = new LinkedHashMap<String, String>();
        credentials.put(Constants.USERNAME, username);
        credentials.put(Constants.PASSWORD, password);
        credentials.put(Constants.STATUS, Status.ACTIVE.getStatus());
        credentials.put(Constants.IS_TERMINATED, Status.INACTIVE.getStatus());
        return employeeDao.findByAttributes(credentials);
    }

    /**
     * Saves user information
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param userInfo
     * @return
     * @throws SQLException
     */
    public String saveUserInfo(Map<String, String> userInfo) throws SQLException {
        Employee employee = new Employee();
        parseUserInfo(employee.getAttributes(), userInfo);
        employeeDao.setEmployeeObject(userInfo, employee);
        save(employee);
        return employee.getId() != null ? employee.getId() : null;

    }

    /**
     * Terminates an employee
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param id
     * @throws SQLException
     */
    public void terminateEmployee(String id) throws SQLException {
        Employee employee = employeeDao.find(id);
        boolean isTerminated = false;
        if (employee != null) {
            employee.setIsTerminated(Status.ACTIVE);
            isTerminated = employeeDao.update(employee);
            if (isTerminated) {
                LOGGER.info(Constants.SUCCESS_TERMINATE_USER);
            } else {
                LOGGER.severe(Constants.FAIL_TERMINATE_USER);
            }
        } else {
            LOGGER.warning(Constants.EMPLOYEE_NOT_FOUND);
        }
    }

    /**
     * Deletes user
     * 
     * @param id
     * @throws SQLException
     */
    public void delete(String id) throws SQLException {
        Employee employee = employeeDao.find(id);
        boolean isDeleted = false;
        if (employee != null) {
            employee.setStatus(Status.INACTIVE);
            isDeleted = employeeDao.update(employee);
            if (isDeleted) {
                LOGGER.info(Constants.SUCCESS_DELETE_USER);
            } else {
                LOGGER.severe(Constants.FAIL_DELETE_USER);
            }
        } else {
            LOGGER.warning(Constants.EMPLOYEE_NOT_FOUND);
        }
    }

    /**
     * Gets user registration informations
     * 
     * @author Niraj Rajbhandari<nirajrajbhandari@lftechnology.com>
     * 
     * @param scanner
     * @param console
     * @return
     */
    public Map<String, String> getUserRegistrationInfo(Scanner scanner, Console console) {
        Map<String, String> userRegistrationInfo = new LinkedHashMap<String, String>();
        userRegistrationInfo.put(Constants.FULLNAME, userInput.getInput(scanner, console, Constants.FULLNAME_LABEL));
        userRegistrationInfo.put(Constants.USERNAME, userInput.getInput(scanner, console, Constants.USERNAME_LABEL));
        userRegistrationInfo.put(Constants.PASSWORD, userInput.getInput(scanner, console, Constants.PASSWORD_LABEL));
        userRegistrationInfo.put(Constants.ADDRESS, userInput.getInput(scanner, console, Constants.ADDRESS_LABEL));
        LOGGER.info(Constants.DEPARTMENT_LABEL);
        int department = userNumInput.getInput(scanner, 1, 8);
        userRegistrationInfo.put(Constants.DEPARTMENT, getDepartmentById(department));
        LOGGER.info(Constants.ROLE_LABEL);
        int role = userNumInput.getInput(scanner, 1, 2);
        if (role == 1) {
            userRegistrationInfo.put(Constants.ROLE, Roles.ADMIN.getRole());
        } else {
            userRegistrationInfo.put(Constants.ROLE, Roles.USER.getRole());
        }

        return userRegistrationInfo;
    }

    /**
     * Gets user credentials
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param scanner
     * @param console
     * @return
     */
    public Map<String, String> getUserCredentials(Scanner scanner, Console console) {
        Map<String, String> credentials = new LinkedHashMap<String, String>();
        credentials.put(Constants.USERNAME, userInput.getInput(scanner, console, Constants.USERNAME_LABEL));
        credentials.put(Constants.PASSWORD, userInput.getInput(scanner, console, Constants.PASSWORD_LABEL));
        return credentials;
    }

    /**
     * Registers a user
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param employeeInfo
     * @return
     * @throws SQLException
     */
    public Map<String, String> registerUser(Map<String, String> employeeInfo) throws SQLException {
        Map<String, String> userExistsCondition = new LinkedHashMap<String, String>();
        Map<String, String> employeeAfterRegistration = null;
        userExistsCondition.put(Constants.USERNAME, employeeInfo.get(Constants.USERNAME));

        if (!employeeDao.exists(userExistsCondition)) {
            String userId = saveUserInfo(employeeInfo);

            if (userId != null) {
                Employee employee = find(userId);
                employeeAfterRegistration = employee.getInfo();
            }
        } else {
            LOGGER.warning(Constants.USER_ALREADY_EXIST);
        }

        return employeeAfterRegistration;

    }

    /**
     * Adds a user
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param scanner
     * @param console
     * @return
     * @throws SQLException
     * @throws CustomException
     */
    public Employee addUser(Scanner scanner, Console console) throws SQLException {
        Employee employee = new Employee();
        Map<String, String> userRegistrationInfo = getUserRegistrationInfo(scanner, console);
        userRegistrationInfo = registerUser(userRegistrationInfo);

        if (userRegistrationInfo != null && !Utils.isEmpty(userRegistrationInfo.get(Constants.USERNAME))) {
            employee = setAttributes(employee, userRegistrationInfo);
        } else {
            LOGGER.warning(Constants.FAIL_USER_REGISTER);
        }
        return employee;
    }

    /**
     * Get department by id
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param id
     * @return
     */
    private String getDepartmentById(int id) {
        String department;
        switch (id) {
        case 2:
            department = Constants.ROR;
            break;
        case 3:
            department = Constants.PHP;
            break;
        case 4:
            department = Constants.DEVOPS;
            break;
        case 5:
            department = Constants.DEVOPS;
            break;
        case 6:
            department = Constants.IOS;
            break;
        case 7:
            department = Constants.ADMINISTRATION;
            break;
        case 8:
            department = Constants.OPERATIONS;
            break;
        default:
            department = Constants.JAVA;
            break;
        }
        return department;
    }

    /**
     * Parses user information
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param attributes
     * @param userInfo
     */
    private void parseUserInfo(List<String> attributes, Map<String, String> userInfo) {
        for (String attribute : attributes) {
            if (!userInfo.containsKey(attribute)) {
                userInfo.put(attribute, null);
            }
        }
        if (userInfo.get(Constants.STATUS) == null) {
            userInfo.put(Constants.STATUS, Status.ACTIVE.getStatus());
        }
        if (userInfo.get(Constants.IS_TERMINATED) == null) {
            userInfo.put(Constants.IS_TERMINATED, Status.INACTIVE.getStatus());
        }
    }

    /**
     * Sets attributes
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * 
     * @param employee
     * @param userInfo
     * @return
     */
    public Employee setAttributes(Employee employee, Map<String, String> userInfo) {
        employeeDao.setEmployeeObject(userInfo, employee);
        return employee;

    }

    /**
     * Searches user
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param scanner
     * @param console
     * @return
     * @throws SQLException
     */
    public String searchUser(Scanner scanner, Console console) throws SQLException {
        Map<String, String> searchCondition = new LinkedHashMap<String, String>();
        String username = userInput.getInput(scanner, console, Constants.SEARCH_BY_USERNAME);
        searchCondition.put(Constants.USERNAME, username);
        searchCondition.put(Constants.FULLNAME, username);
        searchCondition.put(Constants.DEPARTMENT, username);
        searchCondition.put(Constants.ADDRESS, username);
        searchCondition.put(Constants.OPERATOR, Operators.AND.getOperator());
        searchCondition.put(Constants.STATUS, Status.ACTIVE.getStatus());
        List<Employee> searchResults = employeeDao.findAll(searchCondition, Operators.OR);
        if (searchResults.isEmpty()) {
            return Constants.USER_NOT_FOUND;
        }
        return getFormattedSearchResults(searchResults);
    }

    /**
     * Edits user profile
     * 
     * @author Niraj Rajbhandari<nirajrajbhandari@lftechnology.com>
     * @param employee
     * @param scanner
     * @param console
     * @throws SQLException
     */
    public void editUserProfile(Employee employee, Scanner scanner, Console console) throws SQLException {
        List<String> choices = userInput.getMultipleChoices(scanner, console, Constants.EDIT_OPTION_LABEL, 1, 4);
        Map<String, String> editInfo = getEditInfo(scanner, console, choices);
        Employee editedEmployee = setAttributes(employee, editInfo);
        save(editedEmployee);
    }

    /**
     * Gets users list
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @return
     * @throws SQLException
     */
    public String getUsersList() throws SQLException {
        Map<String, String> searchCondition = new LinkedHashMap<String, String>();
        searchCondition.put(Constants.STATUS, Status.ACTIVE.getStatus());
        List<Employee> searchResults = employeeDao.findAll(searchCondition);
        if (searchResults.isEmpty()) {
            return Constants.USER_NOT_FOUND;
        }
        return getFormattedSearchResults(searchResults);
    }

    /**
     * Get total users in the db
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @return
     * @throws SQLException
     */
    public int count() throws SQLException {
        return employeeDao.getCount();
    }

    /**
     * Terminates or deletes user
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param scanner
     * @param console
     * @throws SQLException
     */
    public void TerminateDeleteUser(Scanner scanner, Console console, Employee loggedInUser) throws SQLException {
        Map<String, String> condition = new LinkedHashMap<String, String>();
        condition.put(Constants.USERNAME, userInput.getInput(scanner, console, Constants.SELECT_USER_EDIT));
        Employee employee = employeeDao.findByAttributes(condition);
        if (employee.getId() == null) {
            LOGGER.warning(Constants.USER_NOT_FOUND);
        } else {
            editSelectedUser(scanner, employee, loggedInUser);
        }

    }

    /**
     * Gets the users choice to terminate or delete a user
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param scanner
     * @param console
     * @param employee
     * @throws SQLException
     */
    private void editSelectedUser(Scanner scanner, Employee employee, Employee loggedInUser) throws SQLException {
        LOGGER.info(Constants.DELETE_TERMINATE_MENU);
        int selectedAction = userNumInput.getInput(scanner, 1, 3);
        switch (selectedAction) {
        case 1:
            terminateEmployee(employee.getId());
            break;
        case 2:
            delete(employee.getId());
            break;
        case 3:
            RouteServices.routeAfterLogin(loggedInUser);
            break;
        default:
            LOGGER.severe(Constants.INVALID_INPUT);
            RouteServices.routeAfterLogin(loggedInUser);
        }
    }

    /**
     * Gets edit information
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param scanner
     * @param console
     * @param choices
     * @return
     */
    private Map<String, String> getEditInfo(Scanner scanner, Console console, List<String> choices) {
        Map<String, String> editInfo = new LinkedHashMap<String, String>();
        for (String choice : choices) {
            switch (choice) {
            case "2":
                editInfo.put(Constants.ADDRESS, userInput.getInput(scanner, console, Constants.ADDRESS_LABEL));
                break;
            case "3":
                LOGGER.info(Constants.DEPARTMENT_LABEL);
                int department = userNumInput.getInput(scanner, 1, 8);
                editInfo.put(Constants.DEPARTMENT, getDepartmentById(department));
                break;
            case "4":
                editInfo.put(Constants.PASSWORD, userInput.getInput(scanner, console, Constants.PASSWORD_LABEL));
                break;
            default:
                editInfo.put(Constants.FULLNAME, userInput.getInput(scanner, console, Constants.FULLNAME_LABEL));
                break;
            }
        }
        return editInfo;
    }

    /**
     * Formats the search result
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param searchResults
     * @return
     */
    private String getFormattedSearchResults(List<Employee> searchResults) {
        StringBuilder formattedSearchResult = new StringBuilder();
        formattedSearchResult.append(Constants.SEARCH_RESULT_HEADER);
        formattedSearchResult.append(Constants.DIVIDER);
        for (Employee employee : searchResults) {
            formattedSearchResult.append("\n");
            formattedSearchResult.append(employee.getFullname());
            formattedSearchResult.append("\t\t");
            formattedSearchResult.append(employee.getUsername());
            formattedSearchResult.append("\t\t");
            formattedSearchResult.append(employee.getAddress());
            formattedSearchResult.append("\t\t   ");
            formattedSearchResult.append(employee.getDepartment());
            formattedSearchResult.append("\t\t\t");
            formattedSearchResult.append(employee.getRole());
            formattedSearchResult.append("\t\t");
            formattedSearchResult.append(getStatus(employee.getIsTerminated()));
            formattedSearchResult.append("\n");
        }
        return formattedSearchResult.toString();
    }

    /**
     * Gets status string
     * 
     * @author Niraj Rajbhandari <nirajrajbhandari@lftechnology.com>
     * @param status
     * @return
     */
    private String getStatus(String status) {
        String statusString;
        if (status.equals(Status.ACTIVE.getStatus())) {
            statusString = Status.ACTIVE.getStatusString();
        } else {
            statusString = Status.INACTIVE.getStatusString();
        }
        return statusString;
    }

}
