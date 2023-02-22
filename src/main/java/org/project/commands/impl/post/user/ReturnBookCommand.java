package org.project.commands.impl.post.user;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;
import org.project.exceptions.DaoException;
import org.project.services.resources.CommandPath;

import java.sql.Timestamp;

public class ReturnBookCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        int bookId = Integer.parseInt(content.getParameter("bookId"));
        Timestamp startTime = Timestamp.valueOf(content.getParameter("start"));
        Timestamp endTime = Timestamp.valueOf(content.getParameter("end"));

        CheckoutDao checkoutDao = new CheckoutDao();
        checkoutDao.delete(bookId, startTime, endTime);
        BookDao bookDao = new BookDao();
        bookDao.changeCopiesNum(bookId, true);
        return new ActionResult(CommandPath.MY_BOOKS, true);
    }
}
