package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.CheckoutDao;
import org.project.exceptions.DaoException;

import java.sql.Timestamp;

public class ReturnBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) throws DaoException {
        int bookId = Integer.parseInt(content.getParameter("bookId"));
        Timestamp startTime = Timestamp.valueOf(content.getParameter("start"));
        Timestamp endTime = Timestamp.valueOf(content.getParameter("end"));

        CheckoutDao dao = new CheckoutDao();
        dao.deleteCheckout(bookId, startTime, endTime);
        return new CommandResult("front?command=my_books", true);
    }
}
