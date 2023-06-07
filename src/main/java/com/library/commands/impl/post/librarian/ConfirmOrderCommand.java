package com.library.commands.impl.post.librarian;

import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.BookDao;
import com.library.dao.CheckoutDao;
import com.library.exceptions.DaoException;
import com.library.services.resources.CommandPath;


public class ConfirmOrderCommand implements ActionCommand {
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        int checkoutId = Integer.parseInt(content.getParameter("confirmOrder"));
        int bookId = Integer.parseInt(content.getParameter("bookId"));
        CheckoutDao checkoutDao = new CheckoutDao();
        BookDao bookDao = new BookDao();

        checkoutDao.confirm(checkoutId);
        bookDao.changeCopiesNum(bookId, false);

        return new ActionResult(CommandPath.SHOW_ORDERS, true);
    }
}
