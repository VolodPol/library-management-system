package com.library.commands.impl.post;

import com.library.entity.User;
import com.library.services.FineService;
import com.library.services.LoginService;
import com.library.services.PasswordEncryptor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import com.library.utils.CaptchaVerifier;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.CheckoutDao;
import com.library.dao.UserDao;
import com.library.entity.Role;
import com.library.exceptions.DaoException;
import com.library.services.resources.MessageName;
import com.library.services.validation.Validator;
import com.library.services.validation.dataset.DataSetProvider;
import com.library.services.validation.dataset.impl.UserDataSet;
import com.library.services.validation.impl.LoginUserStrategy;
import com.library.utils.PathProvider;
import com.library.utils.UtilProvider;

import static com.library.services.resources.FilePath.*;
import static com.library.utils.PathProvider.getPath;

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
        Validator validator = new Validator(new LoginUserStrategy(dataSet));
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
            if (user.getStatus() == 1) {
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
            content.setRequestAttribute("error", MessageName.NOT_CORRECT_INPUT);
            return new ActionResult(page, false);
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
            userDao.upgradeSub(user.getId(), user.getFineAmount() + fineAmount);
    }
}