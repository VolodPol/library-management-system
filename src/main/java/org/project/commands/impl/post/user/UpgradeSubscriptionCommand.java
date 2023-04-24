package org.project.commands.impl.post.user;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.entity.impl.User;
import org.project.exceptions.DaoException;
import org.project.services.resources.CommandPath;
import org.project.services.resources.MessageName;

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
