package org.project.services.validation;

import org.project.utils.UtilProvider;

import java.sql.Date;
import java.util.regex.Pattern;

public class BookValidator {
    private static final String TITLE_REGEX = "^[A-Za-z,.'\\- ]{3,129}";
    private static final String AUTHOR_REGEX = "^[a-zA-Z]+(([',.-][a-zA-Z ])?[a-zA-Z]*)*$";
    private static final String ISBN_REGEX = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";

    public static boolean validateTitle(String title){
        if (UtilProvider.isEmpty(title)) return false;
        return Pattern.compile(TITLE_REGEX)
                .matcher(title)
                .matches();
    }

    public static boolean validateAuthor(String author) {
        if (UtilProvider.isEmpty(author)) return false;
        final int length = author.length();
        if (length > 3 && length < 256) {
            return Pattern.compile(AUTHOR_REGEX)
                    .matcher(author)
                    .matches();
        }
        return false;
    }

    public static boolean validateCopiesNum(String number) {
        if (UtilProvider.isEmpty(number)) return false;
        int numberValue;
        try {
            numberValue = Integer.parseInt(number);
        } catch (NumberFormatException exception) {
            return false;
        }
        return numberValue > 0 && numberValue < 10_000;
    }

    public static boolean validateIsbn(String isbn) {
        if (UtilProvider.isEmpty(isbn)) return false;
        return Pattern.compile(ISBN_REGEX)
                .matcher(isbn)
                .matches();
    }

    public static boolean validateDate(String date) {
        if (UtilProvider.isEmpty(date)) return false;
        Date dateValue = Date.valueOf(date);
        return dateValue.before(new Date(System.currentTimeMillis())) && dateValue.after(Date.valueOf("1500-01-01"));
    }

    public static boolean validatePublisher(String name) {
        if (UtilProvider.isEmpty(name)) return false;
        return Pattern.compile(TITLE_REGEX)
                .matcher(name)
                .matches();

    }
}
