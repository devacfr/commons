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

import org.apache.commons.lang.builder.EqualsBuilder;

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

    private List<ValidationFailure> failures;

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
    public void addFailure(ValidationFailure failure) {
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
    public List<ValidationFailure> getFailures(Object source) {
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
    public boolean hasFailures(Object source) {
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
    public static boolean nullSafeEquals(Object o1, Object o2) {

        if (o1 == null) {
            return o2 == null;
        }

        // Arrays must be handled differently since equals() only does
        // an "==" for an array and ignores equivalence. If an array, use
        // the Jakarta Commons Language component EqualsBuilder to determine
        // the types contained in the array and do individual comparisons.
        if (o1.getClass().isArray()) {
            EqualsBuilder builder = new EqualsBuilder();
            builder.append(o1, o2);
            return builder.isEquals();
        } else { // It is NOT an array, so use regular equals()
            return o1.equals(o2);
        }
    }
}