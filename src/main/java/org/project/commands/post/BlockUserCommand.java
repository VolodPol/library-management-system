package org.project.commands.post;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.exceptions.DaoException;
import org.project.utils.UtilProvider;

public class BlockUserCommand implements ActionCommand {

    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        int id = Integer.parseInt(content.getParameter("userId"));
        UserDao userBlocker = new UserDao();
        userBlocker.blockUser(id, Boolean.parseBoolean(content.getParameter("action")));

        String page = UtilProvider.getLastPage((String) content.getSessionAttribute("page"));
        content.removeSessionAttribute("page");
        return new CommandResult("front?command=show_users".concat(page), true);
    }
}
