package org.project.commands.post;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;
import org.project.dao.UserDao;
import org.project.entity.*;
import org.project.exceptions.DaoException;
import org.project.services.resources.MessageName;
import org.project.utils.UtilProvider;


import static org.project.services.validation.OrderValidator.validateEndAfter;
import static org.project.services.validation.OrderValidator.validateOrderTime;


public class OrderBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String isbn = content.getParameter("isbn");
        BookDao bookFinder = new BookDao();
        Book book;

        String login = (String) content.getSessionAttribute("login");
        UserDao userFinder = new UserDao();
        User user;

        book = bookFinder.findBook(isbn);
        user = userFinder.findUser(login);


        String startTime = content.getParameter("startTime");
        String endTime = content.getParameter("endTime");

        String orderType = content.getParameter("order_type");
        Type type = Type.valueOf(orderType.toUpperCase());

        if (!validateEndAfter(startTime, endTime)) {
            content.setRequestAttribute("error", MessageName.EARLY_RETURN);
            return new CommandResult("new_order.jsp?isbn=".concat(book.getIsbn()), false);
        }

        if (!validateOrderTime(startTime, endTime, type)) {
            content.setRequestAttribute("error",
                    (type.equals(Type.READING_ROOM) ?
                            MessageName.OUT_OF_SHIFT : MessageName.MAX_TIME));
            return new CommandResult("new_order.jsp?isbn=".concat(book.getIsbn()), false);
        }

        if (user.getSubscription().equals(Subscription.BASIC) && type.equals(Type.SUBSCRIPTION)){
            content.setRequestAttribute("error", MessageName.NO_SUB);
            return new CommandResult("new_order.jsp?isbn=".concat(book.getIsbn()), false);
        }
        CheckoutDao orderWriter = new CheckoutDao();
        orderWriter.createCheckout(
                new Checkout(
                        UtilProvider.toTimestamp(startTime),
                        UtilProvider.toTimestamp(endTime),
                        type,
                        user,
                        book
                )
        );
        return new CommandResult("front?command=books", true);
    }
}
