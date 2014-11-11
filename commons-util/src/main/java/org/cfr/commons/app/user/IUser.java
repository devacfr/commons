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

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This interface represents the common user interface for the security.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public interface IUser {

    /**
     * Gets the login of user.
     *
     * @return Returns the login of user.
     */
    @Nonnull
    String getLogin();

    /**
     * Gets the firstname of user.
     *
     * @return Returns the firstname of user.
     */
    @Nullable
    String getFirstName();

    /**
     * Gets the lastname of user.
     *
     * @return Returns the lastname of user.
     */
    @Nullable
    String getLastName();

    /**
     * Gets the password of user.
     *
     * @return Returns the password.
     */
    @Nonnull
    String getPassword();

    /**
     * Indicates whether the user is activated.
     *
     * @return Returns <code>true</code> if the user is activated, otherwise <code>false</code>.
     */
    boolean isActivated();

    /**
     * Gets the email
     *
     * @return Returns the email string representation.
     */
    @Nullable
    String getEmail();

    /**
     * Gets the list of all groups associated to the user.
     *
     * @return Returns the list of all groups associated to the user.
     */
    @Nonnull
    Set<? extends IGroup> getGroups();

    /**
     * Gets indicating whether user is in group
     *
     * @param group
     *            a group to test (cannot be {@code null}).
     * @return return {@code true} if user is in group, otherwise {@code false}.
     * @throws IllegalArgumentException
     *             Throws {@code group} if {@code null}.
     */
    boolean inGroup(@Nullable IGroup group);

}