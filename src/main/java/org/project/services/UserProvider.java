package org.project.services;

import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.entity.Role;
import org.project.entity.Subscription;
import org.project.entity.User;
import org.project.exceptions.DaoException;
import org.project.services.resources.MessageName;

import static org.project.services.validation.UserValidator.*;


public class UserProvider {
    public static boolean createUser(RequestContent content, Role role) throws DaoException {
        String username = content.getParameter("login");
        String email = content.getParameter("email");
        String password = content.getParameter("password");
        String firstName = content.getParameter("firstname");
        String surname = content.getParameter("surname");
        String phone = content.getParameter("phone");
        String age = content.getParameter("age");

        if (!(validateLogin(username) && validateEmail(email) && validatePassword(password) && validateName(firstName)
                && validateName(surname) && validatePhone(phone) && validateAge(age))) {
            content.setRequestAttribute("error",
                    MessageName.INCORRECT_FORM);
            return false;
        }

        if (LogInChecker.doesUserExist(username, email)) {
            content.setRequestAttribute("error", MessageName.LOGIN_EMAIL);
            return false;
        }
        User currentUser = new User(username, PasswordEncryptor.encrypt(password), email, firstName, surname,
                phone, Integer.parseInt(age), (byte) 0, (byte) 0, role, Subscription.BASIC
        );
        UserDao userCreator = new UserDao();
        if (role.equals(Role.USER)) {
            userCreator.insertUser(currentUser);
        } else if (role.equals(Role.LIBRARIAN)) {
            userCreator.insertLibrarian(currentUser);
        }
        return true;
    }
}