package org.project.services.validation.impl;

import org.project.services.validation.dataset.impl.BookDataSet;
import org.project.utils.UtilProvider;

/**
 * Concrete class for the case of validation of data to edit book.
 * In other words, if some piece of data is empty it's still valid
 * dataset.
 */
public class EditBookValidator extends BookValidator{

    /**
     * Constructor with one parameter
     * @param dataSet BookDataset object
     */
    public EditBookValidator(BookDataSet dataSet) {
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
