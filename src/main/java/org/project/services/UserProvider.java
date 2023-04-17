package org.project.services;

import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.entity.Role;
import org.project.entity.Subscription;
import org.project.entity.User;
import org.project.exceptions.DaoException;
import org.project.services.resources.MessageName;
import org.project.services.validation.Validator;
import org.project.services.validation.dataset.DataSetProvider;
import org.project.services.validation.impl.UserValidator;
import org.project.services.validation.dataset.impl.UserDataSet;
import org.project.utils.CaptchaVerifier;

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

        String captchaResponse = content.getParameter("g-recaptcha-response");
        boolean valid = CaptchaVerifier.verify(captchaResponse);
        if (!valid) {
            content.setRequestAttribute("error", MessageName.CAPTCHA_ERROR);
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