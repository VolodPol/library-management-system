package org.project.services;

import org.project.dao.CheckoutDao;
import org.project.entity.Book;
import org.project.entity.Checkout;
import org.project.entity.dto.OrderedBookDTO;

import java.util.ArrayList;
import java.util.List;

public class UserBooksProvider {
    public static List<OrderedBookDTO> getUserBooks(String subscription, String login) {
        CheckoutDao ordersProvider = new CheckoutDao();
        List<Checkout> orders = ordersProvider.getCheckoutsByLogin(login);

        List<OrderedBookDTO> userBooks = new ArrayList<>();

        for (Checkout order : orders) {
            Book currentBook = order.getBook();
            OrderedBookDTO dto = new OrderedBookDTO();

            dto.setTitle(currentBook.getTitle());
            dto.setAuthor(currentBook.getAuthor());
            dto.setOrderDate(order.getStartTime());
            dto.setReturnDate(order.getEndTime());
            dto.setSubscription(subscription);

            userBooks.add(dto);
        }
        return userBooks;
    }
}
