package com.library.commands.impl.post.user;

import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.BookDao;
import com.library.dao.CheckoutDao;
import com.library.exceptions.DaoException;
import com.library.services.resources.CommandPath;

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
