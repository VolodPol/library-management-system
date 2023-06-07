package com.library.services;

import com.library.commands.RequestContent;
import com.library.entity.Role;
import com.library.exceptions.DaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserProviderTest {
    @Mock
    private UserProvider userProvider;
    @Mock
    private RequestContent content;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUser() throws DaoException {
        when(userProvider.createUser(any(RequestContent.class), any(Role.class))).thenReturn(true);
        assertTrue(userProvider.createUser(content, Role.USER));
    }

    @Test
    public void createUser_False() throws DaoException {
        when(userProvider.createUser(any(RequestContent.class), any(Role.class))).thenReturn(false);
        assertFalse(userProvider.createUser(content, Role.USER));
    }
}