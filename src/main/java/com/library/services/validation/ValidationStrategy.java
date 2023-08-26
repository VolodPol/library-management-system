package com.library.services.validation;

/**
 * An interface represents validation strategy to implement by a concrete strategy
 */
public interface ValidationStrategy {
    boolean validate();
    String getErrorMessage();
}
