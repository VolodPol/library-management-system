package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.UserDaoImpl;

public class DeleteLibrarianCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        int id = Integer.parseInt(content.getParameter("id"));
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.deleteUser(id);

        String page = (String) content.getSessionAttribute("page");
        if (page == null)
            page = "";
        else
            page = "&page=" + page;
        content.removeSessionAttribute("page");
        return new CommandResult("front?command=show_librarians".concat(page), true);
    }
}
