package org.project.services.validation.impl;

import org.project.services.resources.MessageName;
import org.project.services.validation.dataset.impl.UserDataSet;

public class LoginUserValidator extends UserValidator{
    private final UserDataSet dataSet;
    private String errorMessage;

    public LoginUserValidator(UserDataSet dataSet) {
        super(dataSet);
        this.dataSet = dataSet;
    }

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
