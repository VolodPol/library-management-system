package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.UserDao;
import org.project.exceptions.DaoException;

public class BlockUserCommand implements ActionCommand {

    @Override
    public CommandResult execute(SessionRequestContent content) throws DaoException {
        int id = Integer.parseInt(content.getParameter("userId"));
        UserDao userBlocker = new UserDao();
        userBlocker.blockUser(id, true);

        String page = (String) content.getSessionAttribute("page");
        if (page == null) {
            page = "";
        } else {
            page = "&page=" + page;
        }
        content.removeSessionAttribute("page");
        return new CommandResult("front?command=show_users".concat(page), true);
    }
}
