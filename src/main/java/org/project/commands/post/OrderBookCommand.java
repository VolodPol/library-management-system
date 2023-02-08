package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;
import org.project.dao.UserDao;
import org.project.entity.*;
import org.project.exceptions.DaoException;
import org.project.utils.UtilProvider;

import java.sql.Timestamp;


public class OrderBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) throws DaoException {
        String isbn = content.getParameter("isbn");
        BookDao bookFinder = new BookDao();
        Book book;

        String login = (String) content.getSessionAttribute("login");
        UserDao userFinder = new UserDao();
        User user;

        book = bookFinder.findBook(isbn);
        user = userFinder.findUser(login);


        Timestamp startTime = UtilProvider.toTimestamp(content.getParameter("startTime"));
        Timestamp endTime = UtilProvider.toTimestamp(content.getParameter("endTime"));

        String orderType = content.getParameter("order_type");
        Type type = Type.valueOf(orderType.toUpperCase());

        if (user.getSubscription().equals(Subscription.BASIC) && type.equals(Type.SUBSCRIPTION)){
            content.setRequestAttribute("errorMessage", "You cannot order a book on a subscription without reader subscription!");
            return new CommandResult("new_order.jsp?isbn=".concat(book.getIsbn()), false);
        }
        CheckoutDao orderWriter = new CheckoutDao();
        orderWriter.createCheckout(
                new Checkout(
                        startTime,
                        endTime,
                        type,
                        user,
                        book
                )
        );
        return new CommandResult("front?command=books", true);
    }
}
