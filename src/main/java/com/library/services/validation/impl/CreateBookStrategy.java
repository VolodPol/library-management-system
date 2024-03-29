package com.library.services.validation.impl;

import com.library.utils.UtilProvider;
import com.library.services.validation.dataset.impl.BookDataSet;

/**
 * Concrete strategy class for the case of validation during creating of the book.
 * In other words, data for validation cannot be empty, otherwise
 * it fails to validate.
 */
public class CreateBookStrategy extends BookStrategy {
    /**
     * Constructor with one parameter
     * @param dataSet BookDataset object
     */
    public CreateBookStrategy(BookDataSet dataSet){
        super(dataSet);
    }

    @Override
    protected boolean validateTitle(String title) {
        if (UtilProvider.isEmpty(title)) return false;
        return super.validateTitle(title);
    }

    @Override
    protected boolean validateAuthor(String author) {
        if (UtilProvider.isEmpty(author)) return false;
        return super.validateAuthor(author);
    }

    @Override
    protected boolean validateCopiesNum(String number) {
        if (UtilProvider.isEmpty(number)) return false;
        return super.validateCopiesNum(number);
    }

    @Override
    protected boolean validateIsbn(String isbn) {
        if (UtilProvider.isEmpty(isbn)) return false;
        return super.validateIsbn(isbn);
    }

    @Override
    protected boolean validateDate(String date) {
        if (UtilProvider.isEmpty(date)) return false;
        return super.validateDate(date);
    }

    @Override
    protected boolean validatePublisher(String name) {
        if (UtilProvider.isEmpty(name)) return false;
        return super.validatePublisher(name);
    }
}
