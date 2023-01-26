package org.project.commands.post;

import org.project.commands.ActionCommand;
import org.project.commands.CommandResult;
import org.project.commands.SessionRequestContent;
import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;
import org.project.dao.UserDaoImpl;
import org.project.entity.Book;
import org.project.entity.User;

import java.sql.Timestamp;

public class OrderBookCommand implements ActionCommand {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        /*
        1. Отримати книгу за isbn
        2. Отримати користувача за логіном з сесії та знайти з бд
        3. Створити та записати об'єкт замовлення
         */

        String isbn = content.getParameter("isbn");
        BookDao bookFinder = new BookDao();
        Book book = bookFinder.findBook(isbn);

        String login = (String) content.getSessionAttribute("login");
        UserDaoImpl userFinder = new UserDaoImpl();
        User user = userFinder.findUser(login);

        String startParam = content.getParameter("startTime");
        Timestamp startTime = Timestamp.valueOf(startParam.replace('T', ' ').concat(":00"));

        String endParam = content.getParameter("endTime");
        Timestamp endTime = Timestamp.valueOf(endParam.replace('T', ' ').concat(":00"));

        CheckoutDao orderWriter = new CheckoutDao();
        orderWriter.createCheckout(
                user,
                book,
                startTime,
                endTime
        );
        return new CommandResult("front?command=books", true);
    }
}
