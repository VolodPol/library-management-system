package com.library.commands.impl.post.admin;

import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.UserDao;
import com.library.exceptions.DaoException;
import com.library.services.resources.CommandPath;
import com.library.utils.UtilProvider;


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
