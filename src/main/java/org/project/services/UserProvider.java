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

public class UserProvider {
    public static boolean createUser(RequestContent content, Role role) throws DaoException {
        UserDataSet data = DataSetProvider.getUserDataSet(content);

        Validator validator = new UserValidator(data);
        boolean validResult = validator.validate();
        if (!validResult) {
            content.setRequestAttribute("error", validator.getErrorMessage());
            return false;
        }

        if (LoginService.doesUserExist(data.getUsername(), data.getEmail())) {
            content.setRequestAttribute("error", MessageName.LOGIN_EMAIL);
            return false;
        }
        User currentUser = new User(data.getUsername(), PasswordEncryptor.encrypt(data.getPassword()), data.getEmail(), data.getFirstName(), data.getSurname(),
                data.getPhone(), Integer.parseInt(data.getAge()), (byte) 0, (byte) 0, role, Subscription.BASIC
        );
        UserDao userDao = new UserDao();
        userDao.insertUser(currentUser, role);
        return true;
    }
}