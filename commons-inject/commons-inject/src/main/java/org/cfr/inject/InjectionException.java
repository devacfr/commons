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
package org.cfr.inject;

import javax.annotation.Nonnull;

/**
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public class InjectionException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new ${code InjectionException} without detail message.
     */
    public InjectionException() {
    }

    /**
     * Constructs an exception with the specified message with an optional list of message formatting arguments. Message
     * formatting rules follow "String.format(..)" conventions.
     */
    public InjectionException(@Nonnull final String messageFormat, @Nonnull final Object... messageArgs) {
        super(String.format(messageFormat, messageArgs));
    }

    /**
     * Constructs an exception wrapping another exception thrown elsewhere.
     */
    public InjectionException(@Nonnull final Throwable cause) {
        super(cause);
    }

    public InjectionException(@Nonnull final String messageFormat, @Nonnull final Throwable cause,
            @Nonnull final Object... messageArgs) {
        super(String.format(messageFormat, messageArgs), cause);
    }
}
