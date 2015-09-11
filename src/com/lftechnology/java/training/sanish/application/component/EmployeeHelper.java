package com.lftechnology.java.training.sanish.application.component;

import com.lftechnology.java.training.sanish.application.model.dao.UserDao;
import com.lftechnology.java.training.sanish.application.model.domain.User;
import com.lftechnology.java.training.sanish.application.utility.UserInput;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provide functions of employee related
 *
 * @author Sanish Maharjan <sanishmaharjan@lftechnology.com>
 */
public class EmployeeHelper {
    private static final Logger LOGGER = Logger.getLogger(EmployeeHelper.class.getName());

    /**
     * Change user name of user
     *
     * @param inputScanner {@link Scanner}
     * @param user         {@link User}
     * @author Sanish Maharjan <sanishmaharjan@lftechnology.com>
     */
    public static void changeUserName(Scanner inputScanner, User user) {
        String message = "\n UserName : " + user.getUserName() + "\n" + ">> Change userName ? n/y :";
        LOGGER.log(Level.INFO, message);
        String edit = UserInput.getString(inputScanner);
        if (edit.equals("yes") || edit.equals("y")) {
            int updateRow = 0;
            String userName;
            String SetStatement;
            String whereCondition = "userId=?";
            UserDao userDao = new UserDao();
            while (updateRow == 0) {
                message = "\n >> Type new userName : ";
                LOGGER.log(Level.INFO, message);
                userName = UserInput.getString(inputScanner);
                SetStatement = "userName=?";
                updateRow = userDao.update(whereCondition, SetStatement, userName, user.getUserId());
                if (updateRow == 0) {
                    message = "\n Error Message: Fail to update userName. Try again? n/y";
                    LOGGER.log(Level.INFO, message);
                    edit = UserInput.getString(inputScanner);
                    if (!edit.equals("y") && !edit.equals("yes")) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Change email of user
     *
     * @param inputScanner {@link Scanner}
     * @param user         {@link User}
     * @author Sanish Maharjan <sanishmaharjan@lftechnology.com>
     */
    public static void changeUserEmail(Scanner inputScanner, User user) {
        String message = "\n User Email : " + user.getEmail() + "\n" + ">> Change user email ? n/y :";
        LOGGER.log(Level.INFO, message);
        String edit = UserInput.getString(inputScanner);
        if (edit.equals("y") || edit.equals("yes")) {
            int updateRow = 0;
            String email;
            String SetStatement;
            String whereCondition = "userId=?";
            UserDao userDao = new UserDao();
            while (updateRow == 0) {
                message = "\n >> Type new email : ";
                LOGGER.log(Level.INFO, message);
                email = UserInput.getString(inputScanner);
                SetStatement = "email=?";
                updateRow = userDao.update(whereCondition, SetStatement, email, user.getUserId());
                if (updateRow == 0) {
                    message = "\n Error Message: Fail to update user email. Try again? n/y";
                    LOGGER.log(Level.INFO, message);
                    edit = UserInput.getString(inputScanner);
                    if (!edit.equals("y") && edit.equals("yes")) {
                        break;
                    }
                }
            }
        }
    }
}
