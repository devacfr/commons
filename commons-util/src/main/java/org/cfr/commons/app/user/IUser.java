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

/**
 * This interface represents the common user interface for the security.
 * 
 * @author cfriedri
 *
 */
public interface IUser {

    /**
     * Gets the login of user.
     * 
     * @return Returns the login of user.
     */
    String getLogin();

    /**
     * Gets the firstname of user.
     * 
     * @return Returns the firstname of user.
     */
    String getFirstName();

    /**
     * Gets the lastname of user.
     * 
     * @return Returns the lastname of user.
     */
    String getLastName();

    /**
     * Gets the password of user.
     * 
     * @return Returns the password.
     */
    String getPassword();

    /**
     * Indicates wether the user is activated.
     * 
     * @return Returns <code>true</code> if the user is activated, otherwise <code>false</code>.
     */
    boolean isActivated();

    /**
     * Gets the email
     * 
     * @return Returns the email string representation.
     */
    String getEmail();

    /**
     * Gets the list of all groups associated to the user.
     * 
     * @return Returns the list of all groups associated to the user.
     */
    Set<? extends IGroup> getGroups();

    /**
     * Gets indicating wether user is in group
     * 
     * @param group
     * @return return <code>true</code> if user is in group, otherwise <code>false</code>
     */
    boolean inGroup(IGroup group);

}