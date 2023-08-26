package com.library.services.validation.impl;

import com.library.services.validation.dataset.impl.UserDataSet;
import org.junit.jupiter.api.Test;
import com.library.services.resources.MessageName;

import static org.junit.jupiter.api.Assertions.*;

public class LoginUserStrategyTest {
    private UserStrategy validator;
    private UserDataSet dataSet;

    @Test
    public void validate_Blank_Case() {
        dataSet = new UserDataSet(
                "", "", "", "", "", "", ""
        );
        validator = new LoginUserStrategy(dataSet);

        assertFalse(validator.validate());
        assertEquals(MessageName.EMPTY_FORM, validator.getErrorMessage());
    }

    @Test
    public void validate_Correct_Data() {
        dataSet = new UserDataSet(
                "username", "", "285k*J39", "", "", "", ""
        );
        validator = new LoginUserStrategy(dataSet);

        assertTrue(validator.validate());
        assertNull(validator.getErrorMessage());
    }

    @Test
    public void validate_Invalid_Data() {
        dataSet = new UserDataSet(
                "007", "82934.f92", "1234", "James", "Bond", "jf3ei2s", "old"
        );
        validator = new LoginUserStrategy(dataSet);

        assertFalse(validator.validate());
        assertEquals(MessageName.INCORRECT_FORM, validator.getErrorMessage());
    }
}