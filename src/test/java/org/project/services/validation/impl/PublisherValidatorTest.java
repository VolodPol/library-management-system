package org.project.services.validation.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.project.dao.PublisherDao;
import org.project.exceptions.DaoException;
import org.project.services.resources.MessageName;
import org.project.services.validation.dataset.impl.PublisherDataSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PublisherValidatorTest {
    private PublisherValidator validator;
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
        validator = new PublisherValidator(dataSet, dao);

        assertFalse(validator.validate());
        assertEquals(MessageName.EMPTY_FORM, validator.getErrorMessage());
        verify(dao, times(0)).isPresent(dataSet.getPublisherName());
    }

    @Test
    public void validate_Incorrect_Pattern() throws DaoException{
        dataSet = new PublisherDataSet("-82jf*jsl^");
        validator = new PublisherValidator(dataSet, dao);

        assertFalse(validator.validate());
        assertEquals(MessageName.INCORRECT_FORM, validator.getErrorMessage());
        verify(dao, times(0)).isPresent(dataSet.getPublisherName());
    }

    @Test
    public void validate_Not_Found() throws DaoException {
        dataSet = new PublisherDataSet("Random Publisher");
        validator = new PublisherValidator(dataSet, dao);
        when(dao.isPresent(dataSet.getPublisherName())).thenReturn(true);

        assertFalse(validator.validate());
        assertEquals(MessageName.PUBLISHER_EXISTS, validator.getErrorMessage());
        verify(dao, times(1)).isPresent(dataSet.getPublisherName());
    }

    @Test
    public void validate_Correct_Data() throws DaoException {
        dataSet = new PublisherDataSet("New Publisher");
        validator = new PublisherValidator(dataSet, dao);
        when(dao.isPresent(dataSet.getPublisherName())).thenReturn(false);

        assertTrue(validator.validate());
        assertNull(validator.getErrorMessage());
        verify(dao, times(1)).isPresent(dataSet.getPublisherName());
    }
}