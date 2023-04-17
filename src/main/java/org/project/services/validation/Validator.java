package org.project.services.validation;

/**
 * Interface for concrete validators to implement
 */
public interface Validator {
    /**
     * Method for data validation
     * @return boolean to check if valid
     */
    boolean validate ();
    /**
     * Method to extract error message
     * @return string that represents error message
     */
    String getErrorMessage();
}
