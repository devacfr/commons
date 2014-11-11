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

import java.security.Principal;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Interface providing user based operations across various applications.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public interface IUserManager {

    /**
     * Gets user object belongs to username.
     *
     * @param username
     *            the username. Cannot be {@code null} or empty.
     * @return Returns a {@link IUser} object, <b>null</b> otherwise.
     * @throws IllegalArgumentException
     *             if {@code username} is {@code null} or empty.
     */
    @Nullable
    @CheckReturnValue
    IUser getUser(@Nonnull String username);

    /**
     * Returns the username of the currently logged in user or null if no user can be found. If possible, please use
     * {@link #getRemoteUsername(HttpServletRequest)}.
     *
     * @return The user name of the logged in user or null
     */
    @Nullable
    @CheckReturnValue
    String getRemoteUsername();

    /**
     * Returns the username of the currently logged in user or null if no user can be found.
     *
     * @param request
     *            The request to retrieve the username from
     * @return The user name of the logged in user or null
     */
    @Nullable
    @CheckReturnValue
    String getRemoteUsername(Object request);

    /**
     * Returns whether the user is in the specify group
     *
     * @param username
     *            The username to check. Cannot be {@code null} or empty.
     * @param group
     *            The group to check. Cannot be {@code null} or empty.
     * @return {@code true} if the user is in the specified group
     * @throws IllegalArgumentException
     *             if {@code username} or {@code group} are {@code null} or empty.
     */
    boolean isUserInGroup(@Nonnull String username, @Nonnull String group);

    /**
     * Returns {@code true} or {@code false} depending on whether a user has been granted the system admin permission.
     *
     * @param username
     *            The username of the user to check. Cannot be {@code null} or empty.
     * @return {@code true} or {@code false} depending on whether a user has been granted the system admin permission.
     * @throws IllegalArgumentException
     *             if {@code username} is {@code null} or empty.
     */
    boolean isSystemAdmin(@Nonnull String username);

    /**
     * Returns {@code true} or {@code false} depending on whether a user has been granted the admin permission
     *
     * @param username
     *            The username of the user to check. Cannot be {@code null}.
     * @return {@code true} or {@code false} depending on whether the user has been granted the admin permission
     * @throws IllegalArgumentException
     *             if {@code username} is {@code null} or empty.
     */
    boolean isAdmin(@Nonnull String username);

    /**
     * Given a username & password, this method checks whether or not the provided user can be authenticated
     *
     * @param username
     *            Username of the user. Cannot be {@code null}.
     * @param password
     *            Password of the user. Cannot be {@code null}.
     * @return {@code true} if the user can be authenticated, {@code false} otherwise
     * @throws IllegalArgumentException
     *             if {@code username} or {@code password} are {@code null} or empty.
     */
    boolean authenticate(@Nonnull String username, @Nonnull String password);

    /**
     * Returns the user that made this request or {@code null} if this application does not have such a user.
     *
     * @param username
     *            Username of the user a consumer is making a request on behalf of. Cannot be {@code null}.
     * @return {@code Principal} corresponding to the username, {@code null} if the user does not exist.
     * @throws UserResolutionException
     *             thrown if there is a problem resolving the user, such as a failure when accessing an external user
     *             store.
     * @throws IllegalArgumentException
     *             if {@code username} is {@code null} or empty.
     */
    Principal resolve(@Nonnull String username) throws UserResolutionException;
}
