package org.project.services;

import org.project.dao.UserDao;
import org.project.entity.User;
import org.project.exceptions.DaoException;

/**
 * The class for login operations, such as: checking if the same user
 * and checking if the user already exists.
 */
public class LoginService {
    /**
     * UserDao instance
     */
    private final UserDao dao;

    /**
     * The default constructor to
     * initialize the dao
     */
    public LoginService() {
        this.dao = new UserDao();
    }

    /**
     * Single-argument constructor
     * @param dao UserDao object to work with
     */
    public LoginService(UserDao dao) {
        this.dao = dao;
    }

    /**
     * Method retrieves user from the DB by the login,
     * and check for a match
     * @param enteredLogin login from UI input
     * @param enteredPassword password inputted from UI
     * @return boolean to confirm the match
     * @throws DaoException which may occur in dao
     */
    public boolean doesMatch(String enteredLogin, String enteredPassword) throws DaoException {
        User user = dao.findByLogin(enteredLogin).orElse(new User());
        String userLogin = user.getLogin();
        String userPassword = user.getPassword();

        if (userLogin == null) return false;
        return userPassword.equals(enteredPassword);
    }

    /**
     * Checks the existence of the user
     * @param login users' login
     * @param email user's email
     * @return boolean to confirm the existence
     * @throws DaoException which may occur in dao
     */
    public boolean doesUserExist(String login, String email) throws DaoException{
        User user = dao.findByLogin(login).orElse(new User());
        if (user.getLogin() == null) {
            return false;
        }
        return (user.getLogin().equals(login) && user.getEmail().equals(email));
    }
}