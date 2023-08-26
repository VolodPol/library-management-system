package com.library.services.validation;

/**
 * A context class that represents Validation and encapsulates Validation Strategy
 * An example of Strategy pattern
 */
public class Validator {
    private final ValidationStrategy strategy;

    public Validator(ValidationStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Method to extract error message
     * @return string that represents error message
     */
    public String getErrorMessage() {
        return strategy.getErrorMessage();
    }
    /**
     * Method for data validation
     * @return boolean to check if valid
     */
    public boolean validate () {
        return strategy.validate();
    }
}
