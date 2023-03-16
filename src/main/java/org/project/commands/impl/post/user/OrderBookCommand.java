package org.project.commands.impl.post.user;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commands.ActionCommand;
import org.project.commands.ActionResult;
import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;
import org.project.dao.UserDao;
import org.project.entity.*;
import org.project.exceptions.DaoException;
import org.project.services.OrderService;
import org.project.services.resources.CommandPath;
import org.project.services.validation.impl.OrderValidator;
import org.project.services.validation.dataset.impl.OrderDataSet;
import org.project.utils.PathProvider;
import org.project.utils.UtilProvider;

import static org.project.services.resources.FilePath.NEW_ORDER;

public class OrderBookCommand implements ActionCommand {
    private final OrderService service = new OrderService(new CheckoutDao());
    @Override
    public ActionResult execute(RequestContent content, HttpServletResponse response) throws DaoException {
        String isbn = content.getParameter("isbn");
        String login = (String) content.getSessionAttribute("login");

        Book book = new BookDao().findByIsbn(isbn).orElse(new Book());
        if (!service.verifyOrder(book, content))
            return new ActionResult(PathProvider.getPath(NEW_ORDER).concat("?isbn=").concat(book.getIsbn()), false);
        User user = new UserDao().findByLogin(login).orElse(new User());

        String startTime = content.getParameter("startTime");
        String endTime = content.getParameter("endTime");
        String orderType = content.getParameter("order_type");
        OrderDataSet dataSet = new OrderDataSet(startTime, endTime, orderType, user.getSubscription().toString());

        OrderValidator validator = new OrderValidator(dataSet);
        boolean validResult = validator.validate();
        if (!validResult) {
            content.setRequestAttribute("error", validator.getErrorMessage());
            return new ActionResult(PathProvider.getPath(NEW_ORDER).concat("?isbn=").concat(book.getIsbn()), false);
        }

        CheckoutDao orderWriter = new CheckoutDao();
        orderWriter.insert(
                new Checkout(
                        UtilProvider.toTimestamp(startTime),
                        UtilProvider.toTimestamp(endTime),
                        Type.valueOf(orderType.toUpperCase()),
                        user,
                        book
                )
        );
        return new ActionResult(CommandPath.BOOKS, true);
    }
}