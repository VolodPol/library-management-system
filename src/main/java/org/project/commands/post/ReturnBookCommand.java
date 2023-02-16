package org.project.commands.post;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;
import org.project.exceptions.DaoException;

import java.sql.Timestamp;

public class ReturnBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        int bookId = Integer.parseInt(content.getParameter("bookId"));
        Timestamp startTime = Timestamp.valueOf(content.getParameter("start"));
        Timestamp endTime = Timestamp.valueOf(content.getParameter("end"));

        CheckoutDao checkoutDao = new CheckoutDao();
        checkoutDao.deleteCheckout(bookId, startTime, endTime);
        BookDao bookDao = new BookDao();
        bookDao.changeCopiesNum(bookId, true);
        return new CommandResult("front?command=my_books", true);
    }
}
