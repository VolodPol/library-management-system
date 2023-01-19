package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;
import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;
import org.project.dao.UserDaoImpl;
import org.project.entity.Book;
import org.project.entity.User;

import java.sql.Timestamp;

public class OrderBookCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        /*
        1. Отримати книгу за isbn
        2. Отримати користувача за логіном з сесії та знайти з бд
        3. Створити та записати об'єкт замовлення
         */

        String isbn = (String) request.getSession().getAttribute("isbn");
        BookDao bookFinder = new BookDao();
        Book book = bookFinder.findBook(isbn);

        String login = (String) request.getSession().getAttribute("login");
        UserDaoImpl userFinder = new UserDaoImpl();
        User user = userFinder.findUser(login);

        String startParam = request.getParameter("startTime");
        Timestamp startTime = Timestamp.valueOf(startParam.replace('T', ' ').concat(":00"));

        String endParam = request.getParameter("endTime");
        Timestamp endTime = Timestamp.valueOf(endParam.replace('T', ' ').concat(":00"));

        CheckoutDao orderWriter = new CheckoutDao();
        orderWriter.createCheckout(
                user,
                book,
                startTime,
                endTime
        );
        return "front?command=books";
    }
}
