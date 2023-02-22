package org.project.commands.impl.post.librarian;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;
import org.project.exceptions.DaoException;
import org.project.services.resources.CommandPath;


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
