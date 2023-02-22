package org.project.commands.impl.post.admin;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.exceptions.DaoException;
import org.project.services.resources.CommandPath;
import org.project.utils.UtilProvider;


public class DeleteLibrarianCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        int id = Integer.parseInt(content.getParameter("id"));
        UserDao userDao = new UserDao();
        userDao.delete(id);

        String page = UtilProvider.getLastPage((String) content.getSessionAttribute("page"));
        content.removeSessionAttribute("page");
        return new ActionResult(CommandPath.SHOW_LIBRARIANS.concat(page), true);
    }
}
