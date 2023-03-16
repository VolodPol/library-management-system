package org.project.services.validation.impl;

import org.junit.jupiter.api.Test;
import org.project.services.resources.MessageName;
import org.project.services.validation.dataset.impl.BookDataSet;

import static org.junit.jupiter.api.Assertions.*;

class CreateBookValidatorTest {

    private BookValidator validator;
    private BookDataSet dataSet;

    @Test
    public void validate_Empty_Data() {
        dataSet = new BookDataSet("", "", "", "", "", "");
        validator = new CreateBookValidator(dataSet);

        assertFalse(validator.validate());
        assertEquals(MessageName.EMPTY_FORM, validator.getErrorMessage());
    }

    @Test
    public void validate_Incorrect_Input() {
        dataSet = new BookDataSet(
                "12852",
                "FF>83",
                "2kfs4l4e",
                "little",
                "today",
                "232jf2"
        );
        validator = new CreateBookValidator(dataSet);

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
        validator = new CreateBookValidator(dataSet);

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
        validator = new CreateBookValidator(dataSet);

        assertFalse(validator.validate());
        assertEquals(MessageName.INCORRECT_FORM, validator.getErrorMessage());
    }
}