package org.project.commands.impl.post.admin;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.exceptions.DaoException;
import org.project.services.resources.CommandPath;

public class DeleteBookCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String isbn = content.getParameter("isbn");
        BookDao dao = new BookDao();
        dao.delete(isbn);
        return new ActionResult(CommandPath.BOOKS, true);
    }
}
