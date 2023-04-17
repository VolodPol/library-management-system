package org.project.services.validation.impl;

import org.project.services.resources.MessageName;
import org.project.services.validation.dataset.impl.UserDataSet;

/**
 * Concrete class for the case of validation user data when logging in.
 */
public class LoginUserValidator extends UserValidator{
    private final UserDataSet dataSet;
    private String errorMessage;

    /**
     * Single-parameter constructor that call the parent's
     * constructor
     * @param dataSet UserDataSet object
     */
    public LoginUserValidator(UserDataSet dataSet) {
        super(dataSet);
        this.dataSet = dataSet;
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
