package org.project.services;

import org.project.dao.CheckoutDao;
import org.project.dao.PublisherDao;
import org.project.entity.Book;
import org.project.entity.Checkout;
import org.project.entity.Publisher;
import org.project.entity.dto.OrderedBookDTO;
import org.project.exceptions.DaoException;

import java.util.ArrayList;
import java.util.List;

public class UserBooksProvider {
    public static List<OrderedBookDTO> getUserBooks(String subscription, String login) throws DaoException {
        CheckoutDao ordersProvider = new CheckoutDao();
        List<Checkout> orders;
        orders = ordersProvider.getCheckoutsByLogin(login);

        List<OrderedBookDTO> userBooks = new ArrayList<>();

        for (Checkout order : orders) {
            Book currentBook = order.getBook();
            OrderedBookDTO dto = new OrderedBookDTO();

            dto.setBookId(currentBook.getId());
            dto.setTitle(currentBook.getTitle());
            dto.setAuthor(currentBook.getAuthor());
            dto.setOrderDate(order.getStartTime());
            dto.setReturnDate(order.getEndTime());
            dto.setSubscription(subscription);

            userBooks.add(dto);
        }
        return userBooks;
    }
    public static void setPublisher (String name, Book book) throws DaoException{
        PublisherDao publisherDao = new PublisherDao();
        if (!publisherDao.isPresent(name)) {   //якщо паблішера не існує
            publisherDao.insertPublisher(new Publisher(name)); //створюємо в бд нового
            Publisher publisher = publisherDao.findPublisher(name);    //видобуваємо його
            book.setPublisher(publisher); //присвоюємо книзі цього паблішера
        }
    }
}
