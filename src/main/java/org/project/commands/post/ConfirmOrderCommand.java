package org.project.commands.post;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;
import org.project.exceptions.DaoException;


public class ConfirmOrderCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        int checkoutId = Integer.parseInt(content.getParameter("confirmOrder"));
        int bookId = Integer.parseInt(content.getParameter("bookId"));
        CheckoutDao checkoutDao = new CheckoutDao();
        BookDao bookDao = new BookDao();

        checkoutDao.confirmCheckout(checkoutId);
        bookDao.changeCopiesNum(bookId, false);

        return new CommandResult("front?command=show_orders", true);
    }
}
