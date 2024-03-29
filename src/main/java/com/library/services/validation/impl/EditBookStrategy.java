package com.library.services.validation.impl;

import com.library.utils.UtilProvider;
import com.library.services.validation.dataset.impl.BookDataSet;

/**
 * Concrete strategy class for the case of validation of data to edit book.
 * In other words, if some piece of data is empty it's still valid
 * dataset.
 */
public class EditBookStrategy extends BookStrategy {

    /**
     * Constructor with one parameter
     * @param dataSet BookDataset object
     */
    public EditBookStrategy(BookDataSet dataSet) {
        super(dataSet);
    }

    @Override
    protected boolean validateTitle(String title) {
        if (UtilProvider.isEmpty(title)) return true;
        return super.validateTitle(title);
    }

    @Override
    protected boolean validateAuthor(String author) {
        if (UtilProvider.isEmpty(author)) return true;
        return super.validateAuthor(author);
    }

    @Override
    protected boolean validateCopiesNum(String number) {
        if (UtilProvider.isEmpty(number)) return true;
        return super.validateCopiesNum(number);
    }

    @Override
    protected boolean validateIsbn(String isbn) {
        if (UtilProvider.isEmpty(isbn)) return true;
        return super.validateIsbn(isbn);
    }

    @Override
    protected boolean validateDate(String date) {
        if (UtilProvider.isEmpty(date)) return true;
        return super.validateDate(date);
    }

    @Override
    protected boolean validatePublisher(String name) {
        if (UtilProvider.isEmpty(name)) return true;
        return super.validatePublisher(name);
    }
}
