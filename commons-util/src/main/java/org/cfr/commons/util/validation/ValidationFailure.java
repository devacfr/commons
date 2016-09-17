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

/**
 * Define a single failure during the validation process. Implementing classes may store any extra information to help
 * callers to identify the source and reasons for the failure.
 *
 * @see BeanValidationFailure
 * @since 1.0
 */
public interface ValidationFailure extends Serializable {

    /**
     * Gets the object that has generated the failure. For example, if a {@code Person} must have a name and a
     * {@code ValidationFailure} is created when the user attempts to save it, the {@code Person} object would be the
     * failure source.
     *
     * @return Returns the failure's source or null in case a source cannot be defined.
     */
    Object getSource();

    /**
     * @return Returns an user defined error object.
     */
    Object getError();

    /**
     * @return Returns a String representation of the error object. This is used in log messages and exceptions.
     */
    String getDescription();

}
