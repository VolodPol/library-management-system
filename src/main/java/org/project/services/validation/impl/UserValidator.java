package org.project.services.validation.impl;

import org.project.services.resources.MessageName;
import org.project.services.validation.Validator;
import org.project.services.validation.dataset.impl.UserDataSet;

import java.util.regex.Pattern;

public class UserValidator implements Validator {
    /* Encapsulate RequestSessionContent to send errorMessage as Parameter */
    //Apply fabric pattern (get from content ... and define validator)
    //+ interface Validator with method to override - validate - which calls these private methods
    private final UserDataSet dataSet;
    private String errorMessage;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String LOGIN_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9~`!@#$%^&*()_\\-+={}:;|\"'<,>.?/]{8,16}$";
    private static final String NAME_REGEX = "^[a-zA-Z]+(([',.-][a-zA-Z ])?[a-zA-Z]*)*$";
    private static final String PHONE_REGEX = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$";

    public UserValidator(UserDataSet dataSet){
        this.dataSet = dataSet;
    }

    @Override
    public boolean validate() {
        if (dataSet.isBlank()) {
            errorMessage = MessageName.EMPTY_FORM;
            return false;
        }
        boolean result = (validateLogin(dataSet.getUsername()) && validateEmail(dataSet.getEmail()) && validatePassword(dataSet.getPassword())
                && validateName(dataSet.getFirstName()) && validateName(dataSet.getSurname()) && validatePhone(dataSet.getPhone())
                && validateAge(dataSet.getAge()));
        if (!result) errorMessage = MessageName.INCORRECT_FORM;
        return result;
    }
    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    // consider empty or null cases
    protected boolean validateLogin(String login) {
        return Pattern.compile(LOGIN_REGEX)
                .matcher(login)
                .matches();
    }

    protected boolean validatePassword(String password) {
        return Pattern.compile(PASSWORD_REGEX)
                .matcher(password)
                .matches();
    }

    private boolean validateEmail(String email) {
        final int length = email.length();
        if (length > 3 && length < 255)
            return Pattern.compile(EMAIL_REGEX)
                    .matcher(email)
                    .matches();
        return false;
    }
    private boolean validateName(String name) {
        final int length = name.length();
        if (length < 2 || length > 45)
            return false;
        return Pattern.compile(NAME_REGEX)
                .matcher(name)
                .matches();
    }
    private boolean validatePhone(String number) {
        final int length = number.length();
        if (length > 5 && length < 16) {
            return Pattern.compile(PHONE_REGEX)
                    .matcher(number)
                    .matches();
        }
        return false;
    }
    private boolean validateAge(String age) {
        int ageValue;
        try {
            ageValue = Integer.parseInt(age);
        } catch (NumberFormatException exception) {
            return false;
        }
        return ageValue > 0 && ageValue < 120;
    }
}
