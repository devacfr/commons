package org.cfr.commons.util.error;

import java.util.Collection;
import java.util.Map;

/**
 * A very simple interface to collect errors. This is typically used during form validation for collecting field
 * validation errors (use {@link #addError(String, String)}), and general errors
 * ({@link #addErrorMessage(String)}) that aren't field-specific (eg. permission problems).
 */
public interface IErrorCollection {

    /**
     * Add a field-specific error message.
     * @param field Field name, eg. "assignee"
     * @param message Error message.
     */
    void addError(String field, String message);

    /**
     * Add error message relating to system state (not field-specific).
     * @param message Error message.
     */
    void addErrorMessage(String message);

    /**
     * Get all non field-specific error messages.
     * @return Collection of error Strings.
     */
    Collection<String> getErrorMessages();

    /**
     * Populate this ErrorCollection with a new set of messages (existing errors are lost).
     * @param errorMessages List of error message {@link String}s.
     */
    void setErrorMessages(Collection<String> errorMessages);

    /**
     * Get error messages, then get rid of them.
     * @return The (now cleared) error messages.
     */
    Collection<String> getFlushedErrorMessages();

    /**
     * Get all field-specific errors.
     * @return Map of String: String pairs, eg. {"assignee": "Assignee is required"}
     */
    Map<String, String> getErrors();

    /**
     * Populate this ErrorCollection with general and field-specific errors.
     * @param errors ErrorCollection whose errors/messages we obtain.
     */
    void addErrorCollection(IErrorCollection errors);

    /**
     * Append new error messages to those already collected.
     * @param errorMessages Collection of error strings.
     */
    void addErrorMessages(Collection<String> errorMessages);

    /**
     * Append new field-specific errors to those already collected.
     * @param errors of String: String pairs, eg. {"assignee": "Assignee is required"}
     */
    void addErrors(Map<String, String> errors);

    /**
     * Whether any errors (of any type - field-specific or otherwise) have been collected.
     * @return true if any errors (of any type - field-specific or otherwise) have been collected.
     */
    boolean hasAnyErrors();
}