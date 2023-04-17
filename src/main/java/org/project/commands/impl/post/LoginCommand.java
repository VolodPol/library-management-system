package org.project.commands.impl.post;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.project.utils.CaptchaVerifier;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.CheckoutDao;
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
    private final LoginService loginService;
    {
        loginService = new LoginService();
    }
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
        if (loginService.doesMatch(login, PasswordEncryptor.encrypt(dataSet.getPassword()))) {
            UserDao userDao = new UserDao();
            User user = userDao.findByLogin(login).orElse(new User());
            setFines(user, userDao, content, response);

            //check if user is blocked
            if (user.isStatus() == 1) {
                content.setRequestAttribute("error", MessageName.BLOCKED);
                return new ActionResult(PathProvider.getPath(LOGIN), false);
            }
            //captcha verification
            String reCaptchaResponse = content.getParameter("g-recaptcha-response");
            if (!CaptchaVerifier.verify(reCaptchaResponse)) {
                content.setRequestAttribute("error", MessageName.CAPTCHA_ERROR);
                return new ActionResult(PathProvider.getPath(LOGIN), false);
            }

            setSessionAttributes(content, user);
            page = getPath(INDEX);
        } else {
            page = getPath(LOGIN);
        }
        return new ActionResult(page, true);
    }
    private void setSessionAttributes(RequestContent content, User user) {
        content.setSessionAttribute("role", user.getRole().getRoleValue());
        content.setSessionAttribute("name", user.getFirstName() + " " + user.getSurname());
        content.setSessionAttribute("login", user.getLogin());
        content.setSessionAttribute("subscription", user.getSubscription().getValue());
    }
    private void setFines(User user, UserDao userDao, RequestContent content, HttpServletResponse response)
            throws DaoException {
        if (!user.getRole().equals(Role.USER))
            return;
        //calculate fine
        FineService service = new FineService(new CheckoutDao());
        int fines = service.checkOrders(user);
        Cookie[] cookies = content.getCookies();

        int fineAmount = service.calculateFine(user.getLogin(), cookies, fines, user.getFineAmount());
        response.addCookie(new Cookie(UtilProvider.getFineCookie(user.getLogin()), String.valueOf(fines)));

        if (fineAmount != 0)
            userDao.setFineAmount(user.getId(), user.getFineAmount() + fineAmount);
    }
}