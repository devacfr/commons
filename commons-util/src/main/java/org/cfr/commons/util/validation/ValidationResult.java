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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a result of a validation execution. Contains a set of {@link ValidationFailure ValidationFailures}that
 * occured in a given context. All failures are kept in the same order they were added.
 *
 * @since 1.0
 */
public class ValidationResult implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1579697072241636599L;

    private final List<ValidationFailure> failures;

    public ValidationResult() {
        failures = new ArrayList<ValidationFailure>();
    }

    /**
     * Add a failure to the validation result.
     *
     * @param failure
     *            failure to be added. It may not be null.
     * @see ValidationFailure
     */
    public void addFailure(final ValidationFailure failure) {
        if (failure == null) {
            throw new IllegalArgumentException("failure cannot be null.");
        }

        failures.add(failure);
    }

    /**
     * Returns all failures added to this result, or empty list is result has no failures.
     */
    public List<ValidationFailure> getFailures() {
        return Collections.unmodifiableList(failures);
    }

    /**
     * Returns all failures related to the <code>source</code> object, or an empty list if there are no such failures.
     *
     * @param source
     *            it may be null.
     * @see ValidationFailure#getSource()
     */
    public List<ValidationFailure> getFailures(final Object source) {
        ArrayList<ValidationFailure> matchingFailures = new ArrayList<ValidationFailure>(5);
        for (ValidationFailure failure : failures) {
            if (nullSafeEquals(source, failure.getSource())) {
                matchingFailures.add(failure);
            }
        }

        return matchingFailures;
    }

    /**
     * Returns true if at least one failure has been added to this result. False otherwise.
     */
    public boolean hasFailures() {
        return !failures.isEmpty();
    }

    /**
     * @param source
     *            it may be null.
     * @return true if there is at least one failure for <code>source</code>. False otherwise.
     */
    public boolean hasFailures(final Object source) {
        for (ValidationFailure failure : failures) {
            if (nullSafeEquals(source, failure.getSource())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        String separator = System.getProperty("line.separator");

        for (ValidationFailure failure : failures) {
            if (ret.length() > 0) {
                ret.append(separator);
            }
            ret.append(failure);
        }

        return ret.toString();
    }

    /**
     * Compares two objects similar to "Object.equals(Object)". Unlike Object.equals(..), this method doesn't throw an
     * exception if any of the two objects is null.
     */
    private static boolean nullSafeEquals(final Object o1, final Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        if (o1.getClass().isArray()) {
            return java.util.Objects.deepEquals(o1, o2);
        } else {
            return o1.equals(o2);
        }
    }
}