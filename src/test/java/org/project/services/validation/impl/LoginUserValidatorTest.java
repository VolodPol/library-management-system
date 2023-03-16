package org.project.services.validation.impl;

import org.junit.jupiter.api.Test;
import org.project.services.resources.MessageName;
import org.project.services.validation.dataset.impl.UserDataSet;

import static org.junit.jupiter.api.Assertions.*;

public class LoginUserValidatorTest {
    private UserValidator validator;
    private UserDataSet dataSet;

    @Test
    public void validate_Blank_Case() {
        dataSet = new UserDataSet(
                "", "", "", "", "", "", ""
        );
        validator = new LoginUserValidator(dataSet);

        assertFalse(validator.validate());
        assertEquals(MessageName.EMPTY_FORM, validator.getErrorMessage());
    }

    @Test
    public void validate_Correct_Data() {
        dataSet = new UserDataSet(
                "username", "", "285k*J39", "", "", "", ""
        );
        validator = new LoginUserValidator(dataSet);

        assertTrue(validator.validate());
        assertNull(validator.getErrorMessage());
    }

    @Test
    public void validate_Invalid_Data() {
        dataSet = new UserDataSet(
                "007", "82934.f92", "1234", "James", "Bond", "jf3ei2s", "old"
        );
        validator = new LoginUserValidator(dataSet);

        assertFalse(validator.validate());
        assertEquals(MessageName.INCORRECT_FORM, validator.getErrorMessage());
    }
}