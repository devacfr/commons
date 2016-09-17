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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.cfr.commons.util.Assert;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Default implementation of interface {@link IErrorCollection}.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class SimpleErrorCollection implements IErrorCollection {

    /**
     *
     */
    private final Map<String, String> errors;

    /**
     *
     */
    private final List<String> errorMessages;

    /**
     * Create new instance.
     */
    public SimpleErrorCollection() {
        errors = new HashMap<>(2);
        errorMessages = new LinkedList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addError(@Nonnull final String field, @Nonnull final String message) {
        errors.put(Assert.checkHasText(field, "field"), Assert.checkHasText(message, "message"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addErrorMessage(@Nonnull final String message) {
        errorMessages.add(Assert.checkHasText(message, "message"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Collection<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setErrorMessages(@Nonnull final Collection<String> errorMessages) {
        Assert.checkNotNull(errorMessages, "errorMessages");
        this.errorMessages.clear();
        this.errorMessages.addAll(errorMessages);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Collection<String> getFlushedErrorMessages() {
        Collection<String> errors = getErrorMessages();
        this.errorMessages.clear();
        return errors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Map<String, String> getErrors() {
        return errors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addErrorCollection(@Nonnull final IErrorCollection errors) {
        addErrorMessages(Assert.checkNotNull(errors, "errors").getErrorMessages());
        addErrors(errors.getErrors());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addErrorMessages(@Nullable final Iterable<String> incomingMessages) {
        if (incomingMessages != null) {
            for (final String incomingMessage : incomingMessages) {
                addErrorMessage(incomingMessage);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addErrors(@Nullable final Map<String, String> incomingErrors) {
        if (incomingErrors == null) {
            return;
        }
        for (final Map.Entry<String, String> mapEntry : incomingErrors.entrySet()) {
            addError(mapEntry.getKey(), mapEntry.getValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAnyErrors() {
        return errors != null && !errors.isEmpty() || errorMessages != null && !errorMessages.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("errors", getErrors())
                .add("error messages", getErrorMessages())
                .toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(errors, errorMessages);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final SimpleErrorCollection that = (SimpleErrorCollection) o;

        if (!errorMessages.equals(that.errorMessages)) {
            return false;
        }
        if (!errors.equals(that.errors)) {
            return false;
        }

        return true;
    }

}
