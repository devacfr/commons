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
package org.cfr.commons.app.user;

import javax.annotation.Nullable;

/**
 * Thrown if there is a problem when trying to resolve a username to a user, such as a failure in accessing an external
 * user store.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class UserResolutionException extends RuntimeException {

    /**
     * generated serial id.
     */
    private static final long serialVersionUID = 3773290670517882893L;

    /**
     * Constructs a new user resolution exception with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this
     * exception's detail message.
     *
     * @param message
     *            the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
     *            value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public UserResolutionException(@Nullable final String message, @Nullable final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new user resolution exception with the specified cause and a detail message of
     * <tt>(cause==null ? null : cause.toString())</tt> (which typically contains the class and detail message of
     * <tt>cause</tt>). This constructor is useful for runtime exceptions that are little more than wrappers for other
     * throwables.
     *
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
     *            value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public UserResolutionException(@Nullable final Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message. The cause is not initialized, and may
     * subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
     *            method.
     */
    public UserResolutionException(@Nullable final String message) {
        super(message);
    }
}
