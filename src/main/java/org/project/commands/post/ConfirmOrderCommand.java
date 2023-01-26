package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;

public class ConfirmOrderCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        int checkoutId = Integer.parseInt(content.getParameter("confirmOrder"));
        int bookId = Integer.parseInt(content.getParameter("bookId"));
        CheckoutDao dao = new CheckoutDao();
        dao.confirmCheckout(checkoutId);

        BookDao bookDao = new BookDao();
        bookDao.decreaseCopiesNum(bookId);

        return new CommandResult("front?command=show_orders", true);
    }
}
