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

import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;
import org.cfr.commons.util.Assert;

/**
 * ValidationFailure implementation that described a failure of a single named property of a Java Bean object.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class BeanValidationFailure extends SimpleValidationFailure {

    /**
     *
     */
    private static final long serialVersionUID = 2500734679142455099L;

    /**
     * property name.
     */
    private final String property;

    /**
     * Creates new BeanValidationFailure.
     *
     * @param source
     * @param property
     * @param error
     */
    public BeanValidationFailure(final Object source, final String property, final Object error) {
        super(source, error);

        if (source == null && property != null) {
            throw new IllegalArgumentException("ValidationFailure cannot have 'property' when 'source' is null.");
        }

        this.property = property;
    }

    private static String validationMessage(final String attribute, final String message) {
        StringBuilder buffer = new StringBuilder(message.length() + attribute.length() + 5);
        buffer.append('\"').append(attribute).append("\" ").append(message);
        return buffer.toString();
    }

    /**
     * Returns a ValidationFailure if a collection attribute of an object is null or empty.
     *
     * @param bean
     * @param attribute
     * @param value
     * @return
     */
    public static ValidationFailure validateNotEmpty(final Object bean,
        final String attribute,
        final Collection<?> value) {

        if (value == null) {
            return new BeanValidationFailure(bean, attribute, validationMessage(attribute, " is required."));
        }

        if (value.isEmpty()) {
            return new BeanValidationFailure(bean, attribute, validationMessage(attribute, " can not be empty."));
        }

        return null;
    }

    /**
     * A utility method that returns a new ValidationFailure if {@code value} is:
     * <ul>
     * <li>a string is either null or has a length of zero</li>
     * <li>a collection of an object is null or empty</li>
     * </ul>
     * a {@code value} is either {@code null} or empty ; otherwise returns null.
     *
     * @param bean
     * @param attribute
     * @param value
     * @return
     */
    public static ValidationFailure validateMandatory(final Object bean, final String attribute, final Object value) {

        if (value instanceof String) {
            return validateNotEmpty(bean, attribute, (String) value);
        }
        if (value instanceof Collection) {
            return validateNotEmpty(bean, attribute, (Collection<?>) value);
        }
        return validateNotNull(bean, attribute, value);
    }

    /**
     * @param bean
     * @param name
     * @return
     */
    public static ValidationFailure validateMandatory(final Object bean, final String name) {
        Assert.checkNotNull(bean, "bean");
        try {
            Object result = PropertyUtils.getProperty(bean, name);
            return validateMandatory(bean, name, result);
        } catch (Exception ex) {
            throw new RuntimeException("Error validationg bean property: " + //
                    bean.getClass().getName() + "." + name, ex);
        }
    }

    /**
     * A utility method that returns a new ValidationFailure if a {@code value} is null ; otherwise returns null.
     *
     * @param bean
     * @param attribute
     * @param value
     * @return Returns a new ValidationFailure if a {@code value} is null ; otherwise returns null.
     */
    public static ValidationFailure validateNotNull(final Object bean, final String attribute, final Object value) {

        if (value == null) {
            return new BeanValidationFailure(bean, attribute, validationMessage(attribute, " is required."));
        }

        return null;
    }

    /**
     * A utility method that returns a ValidationFailure if a string {@code value} is either null or has a length of
     * zero; otherwise returns null.
     *
     * @param bean
     * @param attribute
     * @param value
     * @return
     */
    public static ValidationFailure validateNotEmpty(final Object bean, final String attribute, final String value) {

        if (value == null || value.length() == 0) {
            return new BeanValidationFailure(bean, attribute, validationMessage(attribute, " is a required field."));
        }
        return null;
    }

    /**
     * A utility method that checks that a given string is a valid Java full class name, returning a non-null
     * ValidationFailure if this is not so. Special case: primitive arrays like byte[] are also handled as a valid java
     * class name.
     *
     * @param bean
     * @param attribute
     * @param identifier
     * @return
     */
    public static ValidationFailure validateJavaClassName(final Object bean,
        final String attribute,
        String identifier) {

        ValidationFailure emptyFailure = validateNotEmpty(bean, attribute, identifier);
        if (emptyFailure != null) {
            return emptyFailure;
        }

        char c = identifier.charAt(0);
        if (!Character.isJavaIdentifierStart(c)) {
            return new BeanValidationFailure(bean, attribute,
                    validationMessage(attribute, " starts with invalid character: " + c));
        }

        // handle arrays
        if (identifier.endsWith("[]")) {
            identifier = identifier.substring(0, identifier.length() - 2);
        }

        boolean wasDot = false;
        for (int i = 1; i < identifier.length(); i++) {
            c = identifier.charAt(i);

            if (c == '.') {
                if (wasDot || i + 1 == identifier.length()) {
                    return new BeanValidationFailure(bean, attribute,
                            validationMessage(attribute, " is not a valid Java Class Name: " + identifier));
                }

                wasDot = true;
                continue;
            }

            if (!Character.isJavaIdentifierPart(c)) {
                return new BeanValidationFailure(bean, attribute,
                        validationMessage(attribute, " contains invalid character: " + c));
            }

            wasDot = false;
        }

        return null;
    }

    /**
     * @return Returns a failed property of the failure source object.
     */
    public String getProperty() {
        return property;
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
            String property = getProperty();
            buffer.append(source.getClass().getName()).append('.').append((property == null ? "[General]" : property));
        }
        buffer.append(": ");
        buffer.append(getDescription());
        return buffer.toString();
    }
}
