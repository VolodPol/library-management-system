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

public class BookService {

    private CheckoutDao checkoutDao;
    private PublisherDao publisherDao;
    private BookDao bookDao;

    public BookService() {
    }
    public BookService(CheckoutDao checkoutDao) {
        this.checkoutDao = checkoutDao;
    }
    public BookService(PublisherDao publisherDao, BookDao bookDao) {
        this.publisherDao = publisherDao;
        this.bookDao = bookDao;
    }
    public BookService(CheckoutDao checkoutDao, PublisherDao publisherDao, BookDao bookDao) {
        this.checkoutDao = checkoutDao;
        this.publisherDao = publisherDao;
        this.bookDao = bookDao;
    }

    public List<OrderedBookDTO> getUserBooks(String subscription, String login) throws DaoException {
        initCheckoutDao();
        List<Checkout> orders = checkoutDao.findAllByLogin(login);
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

    public void setPublisher (String name, Book book) throws DaoException{
        if (UtilProvider.isEmpty(name)) return;
        initPublisherDao();
        if (!publisherDao.isPresent(name)) {
            publisherDao.insert(new Publisher(name));
        }
        Publisher publisher = publisherDao.findByName(name).orElse(new Publisher());
        book.setPublisher(publisher);
    }

    public void setIsbn(String isbn, Book book) throws DaoException {
        if (UtilProvider.isEmpty(isbn)) return;
        initBookDao();
        boolean isPresent = bookDao.isIsbnPresent(isbn);
        if (book.getIsbn().equals(isbn) || !isPresent) {
            book.setIsbn(isbn);
        }
    }
    private void initCheckoutDao() {
        if (checkoutDao == null) checkoutDao = new CheckoutDao();
    }
    private void initPublisherDao() {
        if (publisherDao == null) publisherDao = new PublisherDao();
    }
    private void initBookDao() {
        if (bookDao == null) bookDao = new BookDao();
    }
}
