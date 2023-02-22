package org.project.services;

import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;
import org.project.dao.PublisherDao;
import org.project.entity.Book;
import org.project.entity.Checkout;
import org.project.entity.Publisher;
import org.project.entity.dto.OrderedBookDTO;
import org.project.exceptions.DaoException;
import org.project.utils.UtilProvider;

import java.util.ArrayList;
import java.util.List;

public class UserBooksManager {
    public static List<OrderedBookDTO> getUserBooks(String subscription, String login) throws DaoException {
        CheckoutDao ordersProvider = new CheckoutDao();
        List<Checkout> orders = ordersProvider.findCheckoutsByLogin(login);

        List<OrderedBookDTO> userBooks = new ArrayList<>();

        for (Checkout order : orders) {
            Book currentBook = order.getBook();
            OrderedBookDTO dto = new OrderedBookDTO();

            dto.setBookId(currentBook.getId());
            dto.setTitle(currentBook.getTitle());
            dto.setAuthor(currentBook.getAuthor());
            dto.setOrderDate(order.getStartTime());
            dto.setReturnDate(order.getEndTime());
            dto.setFinedStatus(UtilProvider.isFined(order.getFinedStatus()));
            dto.setSubscription(subscription);

            userBooks.add(dto);
        }
        return userBooks;
    }
    public static void setPublisher (String name, Book book) throws DaoException{
        if (UtilProvider.isEmpty(name)) return;
        PublisherDao publisherDao = new PublisherDao();
        if (!publisherDao.isPresent(name)) {
            publisherDao.insert(new Publisher(name));
        }
        Publisher publisher = publisherDao.findByName(name);
        book.setPublisher(publisher);
    }

    public static void setIsbn(String isbn, String formerIsbn, Book book, BookDao dao) throws DaoException {
        if (UtilProvider.isEmpty(isbn)) return;
        boolean isPresent = dao.isIsbnPresent(isbn);
        if (formerIsbn.equals(isbn) || !isPresent) {
            book.setIsbn(isbn);
        }
    }
}
