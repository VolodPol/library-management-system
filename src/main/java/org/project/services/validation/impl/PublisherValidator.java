package org.project.services.validation.impl;

import org.project.dao.BookDao;
import org.project.dao.PublisherDao;
import org.project.exceptions.DaoException;
import org.project.services.resources.MessageName;
import org.project.services.validation.Validator;
import org.project.services.validation.dataset.impl.PublisherDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * The class for publisher data validation
 */
public class PublisherValidator implements Validator {
    private final PublisherDataSet dataSet;
    private String errorMessage;
    private final PublisherDao dao;

    private final static Logger log = LoggerFactory.getLogger(BookDao.class);
    private static final String PUBLISHER_REGEX = "^[A-Za-z,.'\\- ]{3,129}";

    /**
     * The constructor with two arguments to init dao and set dataSet
     * @param dataSet PublisherDataSet object
     * @param dao PublisherDao object to work with
     */
    public PublisherValidator(PublisherDataSet dataSet, PublisherDao dao) {
        this.dataSet = dataSet;
        this.dao = dao;
    }

    @Override
    public boolean validate() {
        if (dataSet.isBlank()) {
            errorMessage = MessageName.EMPTY_FORM;
            return false;
        }
        return (validatePattern(dataSet.getPublisherName()) && validatePresence(dataSet.getPublisherName()));
    }
    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    private boolean validatePattern(String publisher) {
        boolean result = Pattern.compile(PUBLISHER_REGEX)
                .matcher(publisher)
                .matches();
        if (!result) errorMessage = MessageName.INCORRECT_FORM;
        return result;
    }

    /**
     * The method validates presence by using PublisherDao.isPresent() method
     * {@link PublisherDao#isPresent(String)}.
     * @param publisher publisher's name
     * @return boolean as a result
     */
    private boolean validatePresence(String publisher) {
        boolean result = true;
        try {
            result = dao.isPresent(publisher);
        } catch (DaoException e) {
            log.error(String.format("couldn't validate publisher presence in %s class", this.getClass().getName()), e);
        }
        if (result) errorMessage = MessageName.PUBLISHER_EXISTS;
        return !result;
    }
}
