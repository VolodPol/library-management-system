package com.library.services.validation.impl;

import com.library.exceptions.DaoException;
import com.library.services.validation.dataset.impl.PublisherDataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.library.dao.PublisherDao;
import com.library.services.resources.MessageName;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PublisherStrategyTest {
    private PublisherStrategy validator;
    private PublisherDataSet dataSet;
    @Mock
    private PublisherDao dao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void validate_Empty_Form() throws DaoException {
        dataSet = new PublisherDataSet("");
        validator = new PublisherStrategy(dataSet, dao);

        assertFalse(validator.validate());
        assertEquals(MessageName.EMPTY_FORM, validator.getErrorMessage());
        verify(dao, times(0)).isPresent(dataSet.getPublisherName());
    }

    @Test
    public void validate_Incorrect_Pattern() throws DaoException{
        dataSet = new PublisherDataSet("-82jf*jsl^");
        validator = new PublisherStrategy(dataSet, dao);

        assertFalse(validator.validate());
        assertEquals(MessageName.INCORRECT_FORM, validator.getErrorMessage());
        verify(dao, times(0)).isPresent(dataSet.getPublisherName());
    }

    @Test
    public void validate_Not_Found() throws DaoException {
        dataSet = new PublisherDataSet("Random Publisher");
        validator = new PublisherStrategy(dataSet, dao);
        when(dao.isPresent(dataSet.getPublisherName())).thenReturn(true);

        assertFalse(validator.validate());
        assertEquals(MessageName.PUBLISHER_EXISTS, validator.getErrorMessage());
        verify(dao, times(1)).isPresent(dataSet.getPublisherName());
    }

    @Test
    public void validate_Correct_Data() throws DaoException {
        dataSet = new PublisherDataSet("New Publisher");
        validator = new PublisherStrategy(dataSet, dao);
        when(dao.isPresent(dataSet.getPublisherName())).thenReturn(false);

        assertTrue(validator.validate());
        assertNull(validator.getErrorMessage());
        verify(dao, times(1)).isPresent(dataSet.getPublisherName());
    }
}