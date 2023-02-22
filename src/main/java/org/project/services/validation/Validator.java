package org.project.services.validation;

public interface Validator {
    boolean validate ();
    String getErrorMessage();
}
