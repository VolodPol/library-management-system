package org.project.services.pagination.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.project.commands.RequestContent;
import org.project.dao.UserDao;
import org.project.entity.impl.Role;
import org.project.entity.impl.User;
import org.project.exceptions.DaoException;
import org.project.services.pagination.Paginator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserPaginatorTest {
    private Paginator<User> paginator;
    @Mock
    private UserDao userDao;
    @Mock
    private RequestContent content;

    @ParameterizedTest
    @MethodSource({"provideArguments"})
    public void provideData(int page, int allRecords, String usersRole) throws DaoException {
        MockitoAnnotations.openMocks(this);

        when(content.getParameter("page")).thenReturn(String.valueOf(page));
        when(content.getRequestAttribute("usersRole")).thenReturn(usersRole);

        List<User> users = provideUserList();
        when(userDao.findAll((page - 1) * 5, 5,
                Role.valueOf(usersRole.toUpperCase())))
                .thenReturn(users);
        when(userDao.getNumOfRecs()).thenReturn(allRecords);

        paginator = new UserPaginator(userDao);

        assertAll(
                () -> assertEquals(users, paginator.provideData(content)),
                () -> assertEquals(page, paginator.getCurrentPage()),
                () -> assertEquals(Math.ceil((double) allRecords / 5), paginator.getNumberOfPages()),
                () -> assertEquals(5, paginator.getRecordsPerPage())
        );

        verify(content, times(1)).getParameter(anyString());
        verify(content, times(1)).getRequestAttribute(anyString());

        verify(userDao, times(1)).findAll((page - 1) * 5, 5,
                Role.valueOf(usersRole.toUpperCase()));
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(1, 15, "user"),
                Arguments.of(3, 12, "librarian"),
                Arguments.of(2, 10, "admin"),
                Arguments.of(4, 7, "librarian"),
                Arguments.of(1, 5, "user")
        );
    }
    private List<User> provideUserList() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {users.add(new User());}
        return users;
    }
}