package org.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.project.dao.BookDao;
import org.project.dao.CheckoutDao;
import org.project.dao.PublisherDao;
import org.project.entity.Book;
import org.project.entity.Checkout;
import org.project.entity.Publisher;
import org.project.entity.Subscription;
import org.project.entity.dto.OrderedBookDTO;
import org.project.exceptions.DaoException;
import org.project.utils.UtilProvider;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {
    @Mock
    private CheckoutDao checkoutDao;
    @Mock
    private PublisherDao publisherDao;
    @Mock
    private BookDao bookDao;
    private BookService bookService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(
                checkoutDao,
                publisherDao,
                bookDao
        );
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    public void getUserBooks(String sub, String login, Timestamp start, Timestamp end, byte fineStatus, int bookId,
                             String title, String author) throws DaoException {
        List<Checkout> orders = provideOrders(start, end, fineStatus, bookId, title, author);
        List<OrderedBookDTO> expectedOutput = provideOrderedDto(bookId, title, author, start, end, fineStatus, sub);

        when(checkoutDao.findAllByLogin(login)).thenReturn(orders);
        List<OrderedBookDTO> actualOutput = bookService.getUserBooks(sub, login);
        assertIterableEquals(expectedOutput, actualOutput);
        verify(checkoutDao, times(1)).findAllByLogin(login);
    }

    private List<Checkout> provideOrders(Timestamp start, Timestamp end, byte fineStatus, int bookId, String title,
                                         String author) {
        List<Checkout> orders = new ArrayList<>();

        Checkout order1 = new Checkout();
        order1.setStartTime(start);
        order1.setEndTime(end);
        order1.setFinedStatus(fineStatus);
        Book book1 = new Book();
        book1.setId(bookId);
        book1.setTitle(title);
        book1.setAuthor(author);
        order1.setBook(book1);

        Checkout order2 = new Checkout();
        order2.setStartTime(start);
        order2.setEndTime(end);
        order2.setFinedStatus(fineStatus);
        Book book2 = new Book();
        book2.setId(bookId);
        book2.setTitle(title);
        book2.setAuthor(author);
        order2.setBook(book2);

        Checkout order3 = new Checkout();
        order3.setStartTime(start);
        order3.setEndTime(end);
        order3.setFinedStatus(fineStatus);
        Book book3 = new Book();
        book3.setId(bookId);
        book3.setTitle(title);
        book3.setAuthor(author);
        order3.setBook(book3);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        return orders;
    }

    private List<OrderedBookDTO> provideOrderedDto(int bookId, String title, String author, Timestamp start,
                                                   Timestamp end, byte fineStatus, String sub) {
        OrderedBookDTO dto1 = new OrderedBookDTO();
        dto1.setBookId(bookId);
        dto1.setTitle(title);
        dto1.setAuthor(author);
        dto1.setOrderDate(start);
        dto1.setReturnDate(end);
        dto1.setFinedStatus(UtilProvider.isFined(fineStatus));
        dto1.setSubscription(sub);

        OrderedBookDTO dto2 = new OrderedBookDTO();
        dto2.setBookId(bookId);
        dto2.setTitle(title);
        dto2.setAuthor(author);
        dto2.setOrderDate(start);
        dto2.setReturnDate(end);
        dto2.setFinedStatus(UtilProvider.isFined(fineStatus));
        dto2.setSubscription(sub);

        OrderedBookDTO dto3 = new OrderedBookDTO();
        dto3.setBookId(bookId);
        dto3.setTitle(title);
        dto3.setAuthor(author);
        dto3.setOrderDate(start);
        dto3.setReturnDate(end);
        dto3.setFinedStatus(UtilProvider.isFined(fineStatus));
        dto3.setSubscription(sub);

        return List.of(dto1, dto2, dto3);
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(Subscription.READER.toString(), "user1", new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 2),
                        new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3), (byte) 0, 1, "book1", "title1", "author1"),
                Arguments.of(Subscription.BASIC.toString(), "user2", new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60),
                        new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2), (byte) 0, 2, "book2", "title2", "author2"),
                Arguments.of(Subscription.READER.toString(), "user3", new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 3),
                        new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 4), (byte) 0, 3, "book3", "title3", "author3")
        );
    }

    @Test
    public void setPublisher_Empty_Name() throws DaoException {
        String empty = "";
        bookService.setPublisher(empty, new Book());
        verify(publisherDao, times(0)).isPresent(empty);
        verify(publisherDao, times(0)).insert(new Publisher());
        verify(publisherDao, times(0)).findByName(empty);
    }


    @Test
    public void setPublisher_Publisher_Not_Found() throws DaoException {
        String name = "randomName";
        when(publisherDao.isPresent(anyString())).thenReturn(false);
        bookService.setPublisher(name, new Book());
        verify(publisherDao, times(0)).insert(new Publisher());
    }

    @Test
    public void setPublisher_Normal_Flow() throws DaoException {
        String name = "name";
        Book book = new Book();
        Publisher publisher = new Publisher(1, "publisher1");
        book.setPublisher(publisher);

        when(publisherDao.isPresent(name)).thenReturn(true);
        when(publisherDao.findByName(name)).thenReturn(Optional.of(publisher));
        Book testBook = new Book();
        bookService.setPublisher(name, testBook);
        assertEquals(testBook, book);
        verify(publisherDao, times(1)).isPresent(name);
    }

    @Test
    public void setIsbn_Empty_Isbn() throws DaoException {
        String empty = "";
        bookService.setIsbn(empty, new Book());
        verify(bookDao, times(0)).isIsbnPresent(empty);
    }

    @Test
    public void setIsbn_Normal_Flow() throws DaoException {
        String isbn1 = "1234";
        Book book1 = new Book();
        book1.setIsbn("4321");

        when(bookDao.isIsbnPresent(isbn1)).thenReturn(false);
        bookService.setIsbn(isbn1, book1);
        assertEquals(book1.getIsbn(), isbn1);

        String isbn2 = "1537";
        Book book2 = new Book();
        book2.setIsbn("1537");
        when(bookDao.isIsbnPresent(isbn2)).thenReturn(true);
        bookService.setIsbn(isbn2, book2);
        assertEquals(book2.getIsbn(), isbn2);

        verify(bookDao, times(2)).isIsbnPresent(anyString());
    }
}