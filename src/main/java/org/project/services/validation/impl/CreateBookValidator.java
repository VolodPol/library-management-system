package org.project.services.validation.impl;

import org.project.services.validation.dataset.impl.BookDataSet;
import org.project.utils.UtilProvider;

/**
 * Concrete class for the case of validation during creating of the book.
 * In other words, data for validation cannot be empty, otherwise
 * it fails to validate.
 */
public class CreateBookValidator extends BookValidator{
    /**
     * Constructor with one parameter
     * @param dataSet BookDataset object
     */
    public CreateBookValidator(BookDataSet dataSet){
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
