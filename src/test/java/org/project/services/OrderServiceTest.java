package org.project.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.project.commands.RequestContent;
import org.project.dao.CheckoutDao;
import org.project.entity.impl.Book;
import org.project.entity.impl.Checkout;
import org.project.exceptions.DaoException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceTest {
    @Mock
    private CheckoutDao dao;
    private Book book;
    private RequestContent content;
    private OrderService service;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setCopiesNumber(3);
        service = new OrderService(dao);
        content = new RequestContent();
    }

    @Test
    public void verityOrder_Should_Return_True() {
        List<Checkout> list = getList(2);
        boolean flag = true;

        try {
            when(dao.findAllByBookId(anyInt())).thenReturn(list);
            flag = service.verifyOrder(book, content);
        } catch (DaoException exception) {
            exception.printStackTrace();
        }
        assertTrue(flag);
    }
    @Test
    public void verifyOrder_Should_Return_False() throws DaoException {
        List<Checkout> list = getList(4);

        when(dao.findAllByBookId(anyInt())).thenReturn(list);
        boolean flag = service.verifyOrder(book, content);
        assertFalse(flag);
    }
    @Test
    public void verifyOrder_Should_Throw_Exception() throws DaoException {
        when(dao.findAllByBookId(anyInt())).thenThrow(DaoException.class);
        DaoException exception = assertThrows(DaoException.class, () -> dao.findAllByBookId(1));

        assertInstanceOf(DaoException.class, exception);
    }

    private static List<Checkout> getList(int num) {
        List<Checkout> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(new Checkout());
        }
        return list;
    }
}
