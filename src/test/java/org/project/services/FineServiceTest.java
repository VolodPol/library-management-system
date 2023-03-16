package org.project.services;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.project.dao.CheckoutDao;
import org.project.entity.Checkout;
import org.project.entity.User;
import org.project.exceptions.DaoException;
import org.project.utils.UtilProvider;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FineServiceTest {
    private FineService service;
    private CheckoutDao dao;

    @BeforeEach
    public void setUp() {
        dao = mock(CheckoutDao.class);
        service = new FineService(dao);
    }

    @Test
    public void checkOrders_Empty_Order_List() throws DaoException {
        User user = new User();
        user.setLogin("TestUser");
        List<Checkout> orders = new ArrayList<>();
        when(dao.findAllByLogin(user.getLogin())).thenReturn(orders);

        final int actual = service.checkOrders(user);
        final int expected = 0;
        assertEquals(expected, actual);
        verify(dao, times(0)).setFineStatus(anyInt(), anyByte());
    }

    @Test
    public void checkOrders_With_No_Fines() throws DaoException {
        User user = new User();
        user.setLogin("TestUser");
        List<Checkout> orders = new ArrayList<>();

        Checkout order = new Checkout();
        order.setEndTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        orders.add(order);

        Checkout order1 = new Checkout();
        order1.setEndTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2));
        orders.add(order1);

        when(dao.findAllByLogin(user.getLogin())).thenReturn(orders);
        assertEquals(0, service.checkOrders(user));
        verify(dao, times(2)).setFineStatus(anyInt(), anyByte());
    }

    @Test
    public void checkOrders_With_Fines() throws DaoException {
        User user = new User();
        user.setLogin("Noname");

        List<Checkout> orders = new ArrayList<>();
        Checkout order1 = new Checkout();
        order1.setEndTime(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
        orders.add(order1);

        Checkout order2 = new Checkout();
        order2.setEndTime(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2));
        orders.add(order2);

        when(dao.findAllByLogin(user.getLogin())).thenReturn(orders);
        assertEquals(2, service.checkOrders(user));
        verify(dao, times(2)).setFineStatus(anyInt(), anyByte());
    }

    @ParameterizedTest
    @MethodSource("diverseCases")
    public void checkOrders_Diverse_Cases(List<Checkout> orders) throws DaoException {
        User user = new User();
        user.setLogin("Tester");

        when(dao.findAllByLogin(user.getLogin())).thenReturn(orders);
        assertEquals(1, service.checkOrders(user));
        verify(dao, times(3)).setFineStatus(anyInt(), anyByte());
    }

    private static Stream<Arguments> diverseCases() {
        List<Checkout> list1 = new ArrayList<>();
        Checkout order1 = new Checkout();
        order1.setEndTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        list1.add(order1);
        Checkout order2 = new Checkout();
        order2.setEndTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2));
        list1.add(order2);
        Checkout order3 = new Checkout();
        order3.setEndTime(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3));
        list1.add(order3);

        List<Checkout> list2 = new ArrayList<>();
        Checkout order4 = new Checkout();
        order4.setEndTime(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
        list2.add(order4);
        Checkout order5 = new Checkout();
        order5.setEndTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2));
        list2.add(order5);
        Checkout order6 = new Checkout();
        order6.setEndTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3));
        list2.add(order6);

        List<Checkout> list3 = new ArrayList<>();
        Checkout order7 = new Checkout();
        order7.setEndTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2));
        list3.add(order7);
        Checkout order8 = new Checkout();
        order8.setEndTime(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 4));
        list3.add(order8);
        Checkout order9 = new Checkout();
        order9.setEndTime(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        list3.add(order9);
        return Stream.of(
                Arguments.of(list1),
                Arguments.of(list2),
                Arguments.of(list3)
        );
    }

    @Test
    public void calculateFine_Empty_Cookies() {
        final int num = 3;
        Cookie[] cookies = new Cookie[num];
        for (int i = 0; i < num; i++) {
            cookies[i] = new Cookie("cookie", "");
        }

        assertEquals(0, service.calculateFine("string", cookies, 0, 0));
    }

    @Test
    public void calculateFine_LastRec_Zero_OldAmount_Positive() {
        //  lastRec = 0,    oldAmount > 0
        String username = "testUser";
        final int actualNum = 2;
        final int oldAmount = 3;

        Cookie cookie1 = new Cookie(UtilProvider.getFineCookie(username), "0");
        Cookie cookie2 = new Cookie(UtilProvider.getFineCookie("noname"), "3");
        Cookie cookie3 = new Cookie(UtilProvider.getFineCookie("noname1"), "1");
        Cookie[] cookies = new Cookie[]{cookie1, cookie2, cookie3};

        assertEquals(0, service.calculateFine(username, cookies, actualNum, oldAmount));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/calculateFine.csv")
    public void calculateFine_ActualSum_BiggerThan_OldAmount(String actual, String old) {
        String username = "username";
        Cookie cookie1 = new Cookie(UtilProvider.getFineCookie(username), "2");
        Cookie cookie2 = new Cookie(UtilProvider.getFineCookie("noname1"), "3");
        Cookie cookie3 = new Cookie(UtilProvider.getFineCookie("noname2"), "1");

        Cookie[] cookies = new Cookie[] {cookie1, cookie2, cookie3};
        final int actualNum = Integer.parseInt(actual);
        final int oldAmount = Integer.parseInt(old);

        assertEquals(actualNum * 50 - oldAmount, service.calculateFine(
                username, cookies, actualNum, oldAmount
        ));
    }

    @ParameterizedTest
    @MethodSource("defaultCases")
    public void calculateFine_Default_Case(int lastRec, int actual, int old) {
        // Filled cookie files,     notEmpty,   lastRec != 0,   actualSum < old
        String username = "username";

        Cookie cookie1 = new Cookie(UtilProvider.getFineCookie(username), String.valueOf(lastRec));
        Cookie cookie2 = new Cookie(UtilProvider.getFineCookie("noname1"), "3");
        Cookie cookie3 = new Cookie(UtilProvider.getFineCookie("noname2"), "2");
        Cookie[] cookies = new Cookie[]{cookie1, cookie2, cookie3};

        assertEquals((actual - lastRec) * 50, service.calculateFine(
                username, cookies, actual, old));
    }

    private static Stream<Arguments> defaultCases() {
        return Stream.of(
                Arguments.of(4, 3, 200),
                Arguments.of(2, 2, 150),
                Arguments.of(3, 5, 300),
                Arguments.of(1, 4, 250)
        );
    }
}