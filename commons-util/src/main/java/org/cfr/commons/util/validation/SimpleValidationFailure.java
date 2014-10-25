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
package org.cfr.commons.util.validation;

/**
 * Represents a generic validation failure that contains failed object and a message describing the failure.
 * 
 * @since 1.0
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