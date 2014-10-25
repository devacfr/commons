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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimpleErrorCollection implements IErrorCollection {

    Map<String, String> errors;

    List<String> errorMessages;

    public SimpleErrorCollection() {
        errors = new HashMap<String, String>(2);
        errorMessages = new LinkedList<String>();
    }

    public void addError(String field, String message) {
        errors.put(field, message);
    }

    public void addErrorMessage(String message) {
        errorMessages.add(message);
    }

    public Collection<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Collection<String> errorMessages) {
        this.errorMessages = new ArrayList<String>(errorMessages);
    }

    public Collection<String> getFlushedErrorMessages() {
        Collection<String> errors = getErrorMessages();
        this.errorMessages = new ArrayList<String>();
        return errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void addErrorCollection(IErrorCollection errors) {
        addErrorMessages(errors.getErrorMessages());
        addErrors(errors.getErrors());
    }

    public void addErrorMessages(Collection<String> incomingMessages) {
        if (incomingMessages != null && !incomingMessages.isEmpty()) {
            for (final String incomingMessage : incomingMessages) {
                addErrorMessage(incomingMessage);
            }
        }
    }

    public void addErrors(Map<String, String> incomingErrors) {
        if (incomingErrors == null) {
            return;
        }
        for (final Map.Entry<String, String> mapEntry : incomingErrors.entrySet()) {
            addError(mapEntry.getKey(), mapEntry.getValue());
        }
    }

    public boolean hasAnyErrors() {
        return (errors != null && !errors.isEmpty()) || (errorMessages != null && !errorMessages.isEmpty());
    }

    @Override
    public String toString() {
        return "Errors: " + getErrors() + "\n" + "Error Messages: " + getErrorMessages();
    }

    @Override
    public int hashCode() {
        int result;
        result = errors.hashCode();
        result = 29 * result + errorMessages.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
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