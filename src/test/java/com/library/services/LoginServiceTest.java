package com.library.services;

import com.library.dao.UserDao;
import com.library.entity.User;
import com.library.exceptions.DaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginServiceTest {
    private LoginService loginService;
    @Mock
    private UserDao userDao = mock(UserDao.class);

    @BeforeEach
    public void setUp() {
        loginService = new LoginService(userDao);
    }

    /*
    Cases:
        - No user (userLogin == null)
        - Passwords are equal
        - Passwords ain't equal
     */

    @Test
    public void doesMatch_No_User_Found() throws DaoException {
        String login = "noname";
        User defaultUser = new User();
        when(userDao.findByLogin(login)).thenReturn(Optional.of(defaultUser));
        assertFalse(loginService.doesMatch(login, "1111"));
        verify(userDao, times(1)).findByLogin(login);
    }

    @ParameterizedTest
    @MethodSource("passwordsCase")
    public void doesMatch_Passwords_Coincide(String login, String password)
            throws DaoException {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        when(userDao.findByLogin(anyString())).thenReturn(Optional.of(user));
        assertTrue(loginService.doesMatch(login, password));
        verify(userDao, times(1)).findByLogin(anyString());
    }

    @ParameterizedTest
    @MethodSource("passwordsCase")
    public void doesMatch_Passwords_Not_Same(String login, String password)
            throws DaoException{
        User user = new User();
        user.setLogin(login);
        user.setPassword("anotherPassword");

        when(userDao.findByLogin(login)).thenReturn(Optional.of(user));
        assertFalse(loginService.doesMatch(login, password));
        verify(userDao, times(1)).findByLogin(login);
    }

    private static Stream<Arguments> passwordsCase() {
        return Stream.of(
                Arguments.of("Logan", "logan2812"),
                Arguments.of("Silverhand", "2047"),
                Arguments.of("Cooper", "intStellar"),
                Arguments.of("Hancock", "smi7834")
        );
    }
    @Test
    public void doesUserExist_Should_Return_False() throws DaoException {
        User emptyUser = new User();
        when(userDao.findByLogin(anyString())).thenReturn(Optional.of(emptyUser));
        assertFalse(loginService.doesUserExist("username", "userEmail"));
        verify(userDao, times(1)).findByLogin("username");
    }

    @ParameterizedTest
    @MethodSource("emailCases")
    public void doesUserExist_Should_Return_True(String login, String email)
            throws DaoException {
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);

        when(userDao.findByLogin(login)).thenReturn(Optional.of(user));
        assertTrue(loginService.doesUserExist(login, email));
        verify(userDao, times(1)).findByLogin(login);
    }

    private static Stream<Arguments> emailCases() {
        return Stream.of(
                Arguments.of("Logan", "logan2812@mail.com"),
                Arguments.of("Silverhand", "s2047@mail.com"),
                Arguments.of("Cooper", "intStellar@mail.com"),
                Arguments.of("Hancock", "smi7834@mail.com")
        );
    }
}