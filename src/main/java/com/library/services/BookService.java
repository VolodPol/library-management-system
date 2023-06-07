package com.library.services;

import com.library.dao.BookDao;
import com.library.dao.CheckoutDao;
import com.library.dao.PublisherDao;
import com.library.entity.Book;
import com.library.entity.Checkout;
import com.library.entity.Publisher;
import com.library.entity.dto.OrderedBookDTO;
import com.library.exceptions.DaoException;
import com.library.utils.UtilProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * The class for operations related to the book entity/
 */
public class BookService {

    private CheckoutDao checkoutDao;
    private PublisherDao publisherDao;
    private BookDao bookDao;

    /**
     * Single-argument constructor
     * @param checkoutDao CheckoutDao instance
     */
    public BookService(CheckoutDao checkoutDao) {
        this.checkoutDao = checkoutDao;
    }
    /**
     * @param publisherDao PublisherDao object
     * @param bookDao BookDa object
     */
    public BookService(PublisherDao publisherDao, BookDao bookDao) {
        this.publisherDao = publisherDao;
        this.bookDao = bookDao;
    }

    /**
     * @param checkoutDao CheckoutDao object
     * @param publisherDao PublisherDao object
     * @param bookDao BookDao object
     */
    public BookService(CheckoutDao checkoutDao, PublisherDao publisherDao, BookDao bookDao) {
        this.checkoutDao = checkoutDao;
        this.publisherDao = publisherDao;
        this.bookDao = bookDao;
    }

    /**
     * Method finds checkouts by login and builds DTO of the OrderedBookDTO {@link OrderedBookDTO}
     * @param subscription user's subscription
     * @param login user's login
     * @return list of OrderedBookDTO
     * @throws DaoException which may occur in dao
     */
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

    /**
     * Checks if a publisher name already exists,
     * if not sets the publisher to the book.
     * @param name publisher's name
     * @param book book entity
     * @throws DaoException which may occur in dao
     */
    public void setPublisher (String name, Book book) throws DaoException{
        if (UtilProvider.isEmpty(name)) return;
        initPublisherDao();
        if (!publisherDao.isPresent(name)) {
            publisherDao.insert(new Publisher(name));
        }
        Publisher publisher = publisherDao.findByName(name).orElse(new Publisher());
        book.setPublisher(publisher);
    }

    /**
     * The method sets the ISBN if it's already the same or is not present
     * @param isbn book's isbn
     * @param book book entity
     * @throws DaoException which may occur in dao
     */
    public void setIsbn(String isbn, Book book) throws DaoException {
        if (UtilProvider.isEmpty(isbn)) return;
        initBookDao();
        boolean isPresent = bookDao.isIsbnPresent(isbn);
        if (book.getIsbn().equals(isbn) || !isPresent) {
            book.setIsbn(isbn);
        }
    }
    /**
     * Helper method to insure CheckoutDao object is initialized
     */
    private void initCheckoutDao() {
        if (checkoutDao == null) checkoutDao = new CheckoutDao();
    }
    /**
     * Helper method to insure PublisherDao object is initialized
     */
    private void initPublisherDao() {
        if (publisherDao == null) publisherDao = new PublisherDao();
    }
    /**
     * Helper method to insure BookDao object is initialized
     */
    private void initBookDao() {
        if (bookDao == null) bookDao = new BookDao();
    }
}
