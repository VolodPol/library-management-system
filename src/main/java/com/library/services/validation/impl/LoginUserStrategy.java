package com.library.services.validation.impl;

import com.library.services.resources.MessageName;
import com.library.services.validation.dataset.impl.UserDataSet;

/**
 * Concrete strategy class for the case of validation user data when logging in.
 */
public class LoginUserStrategy extends UserStrategy {
    private String errorMessage;

    /**
     * Single-parameter constructor that call the parent's
     * constructor
     * @param dataSet UserDataSet object
     */
    public LoginUserStrategy(UserDataSet dataSet) {
        super(dataSet);
    }

    /**
     * Overridden validate method
     * @return boolean to confirm validation result
     */
    @Override
    public boolean validate() {
        if (dataSet.isBlank()) {
            errorMessage = MessageName.EMPTY_FORM;
            return false;
        }
        boolean result = validateLogin(dataSet.getUsername()) && validatePassword(dataSet.getPassword());
        if (!result) errorMessage = MessageName.INCORRECT_FORM;
        return result;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
