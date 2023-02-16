package org.project.commands.post;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.exceptions.DaoException;
import org.project.utils.UtilProvider;


public class DeleteLibrarianCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        int id = Integer.parseInt(content.getParameter("id"));
        UserDao userDao = new UserDao();
        userDao.deleteUser(id);

        String page = UtilProvider.getLastPage((String) content.getSessionAttribute("page"));
        content.removeSessionAttribute("page");
        return new CommandResult("front?command=show_librarians".concat(page), true);
    }
}
