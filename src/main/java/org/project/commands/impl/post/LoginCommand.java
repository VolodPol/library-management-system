package org.project.commands.impl.post;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.entity.Role;
import org.project.entity.User;
import org.project.exceptions.DaoException;
import org.project.services.*;
import org.project.services.resources.MessageName;
import org.project.services.validation.Validator;
import org.project.services.validation.dataset.DataSetProvider;
import org.project.services.validation.dataset.impl.UserDataSet;
import org.project.services.validation.impl.LoginUserValidator;
import org.project.utils.PathProvider;
import org.project.utils.UtilProvider;

import static org.project.services.resources.FilePath.*;
import static org.project.utils.PathProvider.getPath;

public class LoginCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String page;
        //extract data & validate
        UserDataSet dataSet = DataSetProvider.getUserDataSet(content);
        Validator validator = new LoginUserValidator(dataSet);
        boolean validResult = validator.validate();
        if (!validResult) {
            content.setRequestAttribute("error", validator.getErrorMessage());
            return new ActionResult(PathProvider.getPath(LOGIN), false);
        }
        //check if match
        String login = dataSet.getUsername();
        if (LoginService.doesMatch(login, PasswordEncryptor.encrypt(dataSet.getPassword()))) {
            UserDao userDao = new UserDao();
            User user = userDao.findByLogin(login).orElse(new User());//
            setFines(user, userDao, content, response);

            //check if user is blocked
            if (user.isStatus() == 1) {
                content.setRequestAttribute("error", MessageName.BLOCKED);
                return new ActionResult(PathProvider.getPath(LOGIN), false);
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
        return new ActionResult(page, true);
    }

    private void setFines(User user, UserDao userDao, RequestContent content, HttpServletResponse response)
            throws DaoException {
        if (!user.getRole().equals(Role.USER))
            return;
        //calculate fine
        int fines = FineService.checkOrders(user);
        Cookie[] cookies = content.getCookies();

        int fineAmount = FineService.calculateFine(user.getLogin(), cookies, fines, user.getFineAmount());
        response.addCookie(new Cookie(UtilProvider.getFineCookie(user.getLogin()), String.valueOf(fines)));

        if (fineAmount != 0)
            userDao.setFineAmount(user.getId(), user.getFineAmount() + fineAmount);
    }
}