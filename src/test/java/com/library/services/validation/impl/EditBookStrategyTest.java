package com.library.services.validation.impl;

import com.library.services.validation.dataset.impl.BookDataSet;
import org.junit.jupiter.api.Test;
import com.library.services.resources.MessageName;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditBookStrategyTest {
    private BookStrategy validator;
    private BookDataSet dataSet;

    @Test
    public void validate_Empty_Data() {
        dataSet = new BookDataSet("", "", "", "", "", "");
        validator = new EditBookStrategy(dataSet);

        assertFalse(validator.validate());
        assertEquals(MessageName.EMPTY_FORM, validator.getErrorMessage());
    }

    @Test
    public void validate_Incorrect_Input() {
        dataSet = new BookDataSet(
                "-12-192",
                "fj^32*&",
                "jfw9pf2p",
                "many",
                "yesterday",
                "jfs924*"
        );
        validator = new EditBookStrategy(dataSet);

        assertFalse(validator.validate());
        assertEquals(MessageName.INCORRECT_FORM, validator.getErrorMessage());
    }

    @Test
    public void validate_Correct_Input() {
        dataSet = new BookDataSet(
                "Book",
                "J. K. Rowling",
                "052-246-21-36",
                "6",
                "2015-07-14",
                "Publisher"
        );
        validator = new EditBookStrategy(dataSet);

        assertTrue(validator.validate());
        assertNull(validator.getErrorMessage());
    }

    @Test
    public void validate_Some_Fields_Empty() {
        dataSet = new BookDataSet(
                "Book",
                "",
                "052-246-21-36",
                "",
                "2015-07-14",
                "Publisher"
        );
        validator = new EditBookStrategy(dataSet);

        assertTrue(validator.validate());
        assertNull(validator.getErrorMessage());
    }
}
