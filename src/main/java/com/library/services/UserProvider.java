package com.library.services;

import com.library.commands.RequestContent;
import com.library.dao.UserDao;
import com.library.entity.Role;
import com.library.entity.Subscription;
import com.library.entity.User;
import com.library.exceptions.DaoException;
import com.library.services.resources.MessageName;
import com.library.services.validation.Validator;
import com.library.services.validation.dataset.DataSetProvider;
import com.library.services.validation.impl.UserValidator;
import com.library.services.validation.dataset.impl.UserDataSet;

/**
 * The class is meant for creating users and inserting them into DB
 */
public class UserProvider {
    /**
     * UserDao instance for method operations
     */
    private final UserDao userDao;

    /**
     * Default constructor that initializes UserDao field
     */
    public UserProvider() {
        this.userDao = new UserDao();
    }

    /**
     * Method retrieves data from RequestContent and validates it.
     * If data is invalid, error message is set as requestAttribute by
     * means of validator. LoginService checks if user already exists.
     * If not, execution proceeds to calling insertUser method.
     * @param content wrapper of HttpRequest's and HttpSession's content
     * @param role the user's role
     * @return boolean to confirm the result of the method
     * @throws DaoException which may occur in dao
     */
    public boolean createUser(RequestContent content, Role role) throws DaoException {
        UserDataSet data = DataSetProvider.getUserDataSet(content);

        Validator validator = new UserValidator(data);
        boolean validResult = validator.validate();
        if (!validResult) {
            content.setRequestAttribute("error", validator.getErrorMessage());
            return false;
        }

        LoginService service = new LoginService();
        if (service.doesUserExist(data.getUsername(), data.getEmail())) {
            content.setRequestAttribute("error", MessageName.LOGIN_EMAIL);
            return false;
        }

        insertUser(data, role);
        return true;
    }

    /**
     * Insert the user, created from dataset in DB
     * @param dataSet the set of data to validate
     * @param role the user's role
     * @throws DaoException which may occur in dao
     */
    private void insertUser(UserDataSet dataSet, Role role) throws DaoException {
        User currentUser = new User(dataSet.getUsername(), PasswordEncryptor.encrypt(dataSet.getPassword()), dataSet.getEmail(), dataSet.getFirstName(), dataSet.getSurname(),
                dataSet.getPhone(), Integer.parseInt(dataSet.getAge()), (byte) 0, (byte) 0, role, Subscription.BASIC
        );
        userDao.insertUser(currentUser, role);
    }
}