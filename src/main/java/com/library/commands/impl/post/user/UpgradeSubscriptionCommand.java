package com.library.commands.impl.post.user;

import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.UserDao;
import com.library.entity.User;
import com.library.exceptions.DaoException;
import com.library.services.resources.CommandPath;
import com.library.services.resources.MessageName;

public class UpgradeSubscriptionCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String login = (String) content.getSessionAttribute("login");
        UserDao userDao = new UserDao();

        User thisUser = userDao.findByLogin(login).orElse(new User());
        if (thisUser.getFineAmount() > 0) {
            content.setRequestAttribute("errorMessage", MessageName.FINED);
            return new ActionResult(CommandPath.BOOKS, false);

        } else {
            userDao.upgradeSub(login);
        }

        return new ActionResult(CommandPath.BOOKS, true);
    }
}
