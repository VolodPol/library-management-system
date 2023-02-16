package org.project.commands.post;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.entity.Role;
import org.project.entity.User;
import org.project.exceptions.DaoException;
import org.project.services.FineCalculator;
import org.project.services.FineChecker;
import org.project.services.LogInChecker;
import org.project.services.PasswordEncryptor;
import org.project.services.resources.MessageName;

import java.util.Arrays;

import static org.project.services.validation.UserValidator.*;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PagePathConfigurator.getPath;

public class LoginCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String page;

        String login = content.getParameter("login");
        String password = content.getParameter("password");
        if (!(validateLogin(login) && validatePassword(password))) {
            content.setRequestAttribute("error", MessageName.INCORRECT_FORM);
            return new CommandResult("login.jsp", false);
        }

        if (LogInChecker.doesMatch(login, PasswordEncryptor.encrypt(password))) {
            UserDao userDao = new UserDao();
            User user = userDao.findUser(login);
            if (user.getRole().equals(Role.USER)) {

                int fines = FineChecker.checkOrders(user);
                System.out.println(fines);
                Cookie[] cookies = content.getCookies();
                Arrays.stream(cookies).forEach(elem -> System.out.println(elem.getName() + " " + elem.getValue()));

                int fineAmount = FineCalculator.calculate(login, cookies, fines, user.getFineAmount());

                //
                response.addCookie(new Cookie("fines".concat("_" + login), String.valueOf(fines)));
                //
//                userDao.setFineAmount(user.getId(), user.getFineAmount());
                if (fineAmount != 0)
                    userDao.setFineAmount(user.getId(), user.getFineAmount() + fineAmount);

            }

            if (user.isStatus() == 1) {
                content.setRequestAttribute("error", MessageName.BLOCKED);
                return new CommandResult("login.jsp", false);
            }
            String userRole = user.getRole().getRoleValue();

            content.setSessionAttribute("role", userRole);
            content.setSessionAttribute("name", user.getFirstName() + " " + user.getSurname());
            content.setSessionAttribute("login", user.getLogin());
            content.setSessionAttribute("subscription", user.getSubscription().getValue());

            page = getPath(INDEX);
        } else {
            page = getPath(LOGIN);
        }
        return new CommandResult(page, true);
    }
}