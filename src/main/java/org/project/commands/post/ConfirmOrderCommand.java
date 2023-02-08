package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;
import org.project.exceptions.DaoException;


public class ConfirmOrderCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) throws DaoException {
        int checkoutId = Integer.parseInt(content.getParameter("confirmOrder"));
        int bookId = Integer.parseInt(content.getParameter("bookId"));
        CheckoutDao checkoutDao = new CheckoutDao();
        BookDao bookDao = new BookDao();

        checkoutDao.confirmCheckout(checkoutId);
        bookDao.decreaseCopiesNum(bookId);

        return new CommandResult("front?command=show_orders", true);
    }
}
