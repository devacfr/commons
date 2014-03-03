package org.cfr.commons.util.validation;

/**
 * Represents a generic validation failure that contains
 * failed object and a message describing the failure.
 * 
 * @since 1.1
 */
public class SimpleValidationFailure implements ValidationFailure {

    /**
     * 
     */
    private static final long serialVersionUID = 8464118115080226892L;

    protected Object source;

    protected Object error;

    public SimpleValidationFailure(Object source, Object error) {
        this.source = source;
        this.error = error;
    }

    /**
     * Returns the error converted to String.
     */
    public String getDescription() {
        return String.valueOf(error);
    }

    /**
     * Returns object that failed the validation.
     */
    public Object getSource() {
        return source;
    }

    public Object getError() {
        return error;
    }

    /**
     * Returns a String representation of the failure.
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Validation failure for ");
        Object source = getSource();

        if (source == null) {
            buffer.append("[General]");
        } else {
            String sourceLabel = (source instanceof String) ? source.toString() : source.getClass().getName();
            buffer.append(sourceLabel);
        }
        buffer.append(": ");
        buffer.append(getDescription());
        return buffer.toString();
    }
}