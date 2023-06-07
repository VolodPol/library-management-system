package com.library.commands.impl.post.admin;

import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.BookDao;
import com.library.exceptions.DaoException;
import com.library.services.resources.CommandPath;

public class DeleteBookCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String isbn = content.getParameter("isbn");
        BookDao dao = new BookDao();
        dao.delete(isbn);
        return new ActionResult(CommandPath.BOOKS, true);
    }
}
