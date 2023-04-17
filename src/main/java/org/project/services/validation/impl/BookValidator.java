package org.project.services.validation.impl;

import org.project.services.resources.MessageName;
import org.project.services.validation.Validator;
import org.project.services.validation.dataset.impl.BookDataSet;

import java.sql.Date;
import java.util.regex.Pattern;

/**
 * General class for book validation
 */
public class BookValidator implements Validator {
    /**
     * Consists book's data
     */
    private final BookDataSet dataSet;
    /**
     * Encapsulates error message
     */
    private String errorMessage;

    private static final String TITLE_REGEX = "^[A-Za-z,.'\\- ]{3,129}";
    private static final String AUTHOR_REGEX = "^[a-zA-Z]+(([',.-][a-zA-Z ])?[a-zA-Z]*)*$";
    private static final String ISBN_REGEX = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";

    /**
     * Constructor with one parameter to initialize dataSet
     * @param dataSet book BookDataSet object
     */
    public BookValidator(BookDataSet dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * Implementation of validate() method {@link Validator#validate()}, which checks if dataset is not
     * empty and validates with the help of private methods that validate
     * each piece of data by RegExp or other rules
     * @return boolean to confirm that the data is valid
     */
    @Override
    public boolean validate() {
        if (dataSet.isBlank()) {
            errorMessage = MessageName.EMPTY_FORM;
            return false;
        }
        boolean result = (validateTitle(dataSet.getTitle()) && validateAuthor(dataSet.getAuthor())
                && validateIsbn(dataSet.getIsbn()) && validateCopiesNum(dataSet.getCopies())
                && validateDate(dataSet.getDateOfPublication()) && validatePublisher(dataSet.getPublisher()));
        if (!result) errorMessage = MessageName.INCORRECT_FORM;
        return result;
    }
    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    protected boolean validateTitle(String title){
        return Pattern.compile(TITLE_REGEX)
                .matcher(title)
                .matches();
    }

    protected boolean validateAuthor(String author) {
        final int length = author.length();
        if (length > 3 && length < 256) {
            return Pattern.compile(AUTHOR_REGEX)
                    .matcher(author)
                    .matches();
        }
        return false;
    }

    protected boolean validateCopiesNum(String number) {
        int numberValue;
        try {
            numberValue = Integer.parseInt(number);
        } catch (NumberFormatException exception) {
            return false;
        }
        return numberValue > 0 && numberValue < 10_000;
    }

    protected boolean validateIsbn(String isbn) {
        return Pattern.compile(ISBN_REGEX)
                .matcher(isbn)
                .matches();
    }

    protected boolean validateDate(String date) {
        Date dateValue = Date.valueOf(date);
        return dateValue.before(new Date(System.currentTimeMillis())) && dateValue.after(Date.valueOf("1500-01-01"));
    }

    protected boolean validatePublisher(String name) {
        return Pattern.compile(TITLE_REGEX)
                .matcher(name)
                .matches();

    }
}
