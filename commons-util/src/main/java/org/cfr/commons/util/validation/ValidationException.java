package org.cfr.commons.util.validation;

import org.springframework.dao.DataAccessException;

public class ValidationException extends DataAccessException {

    /**
     * 
     */
    private static final long serialVersionUID = 2455617036982816433L;

    private ValidationResult result;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(ValidationResult result) {
        this("Validation failures: " + result.toString(), result);
    }

    public ValidationException(String message, ValidationResult result) {
        super(message);
        this.result = result;
    }

    public ValidationResult getValidationResult() {
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + System.getProperty("line.separator") + this.result;
    }
}