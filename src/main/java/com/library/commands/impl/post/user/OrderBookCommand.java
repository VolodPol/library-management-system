package com.library.commands.impl.post.user;

import com.library.entity.*;
import com.library.services.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import com.library.commands.ActionCommand;
import com.library.commands.ActionResult;
import com.library.commands.RequestContent;
import com.library.dao.BookDao;
import com.library.dao.CheckoutDao;
import com.library.dao.UserDao;
import com.library.exceptions.DaoException;
import com.library.services.resources.CommandPath;
import com.library.services.validation.impl.OrderValidator;
import com.library.services.validation.dataset.impl.OrderDataSet;
import com.library.utils.PathProvider;
import com.library.utils.UtilProvider;

import static com.library.services.resources.FilePath.NEW_ORDER;

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
        orderWriter.insert(new Checkout.CheckoutBuilder()
                .addStartTime(UtilProvider.toTimestamp(startTime))
                .addEndTime(UtilProvider.toTimestamp(endTime))
                .addType(Type.valueOf(orderType.toUpperCase()))
                .addUser(user).addBook(book)
                .build()
        );
        return new ActionResult(CommandPath.BOOKS, true);
    }
}