package com.library.commands.impl.post.admin;

import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.UserDao;
import com.library.exceptions.DaoException;
import com.library.services.resources.CommandPath;
import com.library.utils.UtilProvider;

public class BlockUserCommand implements ActionCommand {

    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        int id = Integer.parseInt(content.getParameter("userId"));
        UserDao userBlocker = new UserDao();
        userBlocker.block(id, Boolean.parseBoolean(content.getParameter("action")));

        String page = UtilProvider.getLastPage((String) content.getSessionAttribute("page"));
        content.removeSessionAttribute("page");
        return new ActionResult(CommandPath.SHOW_USERS.concat(page), true);
    }
}
