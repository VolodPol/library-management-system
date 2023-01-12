package org.project.services;

import org.project.dao.UserDaoImpl;
import org.project.entity.User;

public class LogInChecker {
    private static final UserDaoImpl dao = new UserDaoImpl();
    private static User retrievedUser;

    public static boolean doesMatch(String enteredLogin, String enteredPassword) {
        User user = dao.findUser(enteredLogin);
        String userLogin = user.getLogin();
        String userPassword = user.getPassword();

        if (userLogin == null) return false;
        if (userPassword.equals(enteredPassword)) {
            retrievedUser = user;
            return true;
        } else {
            return false;
        }
    }

    public static User getUser() {
        return retrievedUser;
    }
}