/**
 * Copyright 2014 devacfr<christophefriederich@mac.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cfr.commons.util.error;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

/**
 * A very simple interface to collect errors. This is typically used during form validation for collecting field
 * validation errors (use {@link #addError(String, String)}), and general errors ({@link #addErrorMessage(String)}) that
 * aren't field-specific (eg. permission problems).
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public interface IErrorCollection {

    /**
     * Add a field-specific error message.
     *
     * @param field
     *            Field name, eg. "assignee"
     * @param message
     *            Error message.
     */
    void addError(@Nonnull String field, @Nonnull String message);

    /**
     * Add error message relating to system state (not field-specific).
     *
     * @param message
     *            Error message.
     */
    void addErrorMessage(@Nonnull String message);

    /**
     * Get all non field-specific error messages.
     *
     * @return Collection of error Strings.
     */
    @Nonnull
    Collection<String> getErrorMessages();

    /**
     * Populate this ErrorCollection with a new set of messages (existing errors are lost).
     *
     * @param errorMessages
     *            List of error message {@link String}s.
     */
    void setErrorMessages(@Nonnull Collection<String> errorMessages);

    /**
     * Get error messages, then get rid of them.
     *
     * @return The (now cleared) error messages.
     */
    @Nonnull
    Collection<String> getFlushedErrorMessages();

    /**
     * Get all field-specific errors.
     *
     * @return Map of String: String pairs, eg. {"assignee": "Assignee is required"}
     */
    @Nonnull
    Map<String, String> getErrors();

    /**
     * Populate this ErrorCollection with general and field-specific errors.
     *
     * @param errors
     *            ErrorCollection whose errors/messages we obtain.
     */
    void addErrorCollection(@Nonnull IErrorCollection errors);

    /**
     * Append new error messages to those already collected.
     *
     * @param errorMessages
     *            Collection of error strings.
     */
    void addErrorMessages(@Nonnull Iterable<String> errorMessages);

    /**
     * Append new field-specific errors to those already collected.
     *
     * @param errors
     *            of String: String pairs, eg. {"assignee": "Assignee is required"}
     */
    void addErrors(@Nonnull Map<String, String> errors);

    /**
     * Whether any errors (of any type - field-specific or otherwise) have been collected.
     *
     * @return true if any errors (of any type - field-specific or otherwise) have been collected.
     */
    boolean hasAnyErrors();
}