package org.cfr.commons.app.user;

import java.util.Set;

/**
 * This interface represents the common user interface for the security.
 * @author cfriedri
 *
 */
public interface IUser {

    /**
     * Gets the login of user. 
     * @return Returns the login of user.
     */
    String getLogin();

    /**
     * Gets the firstname of user.
     * @return Returns the firstname of user.
     */
    String getFirstName();

    /**
     * Gets the lastname of user.
     * @return Returns the lastname of user.
     */
    String getLastName();

    /**
     * Gets the password of user.
     * @return Returns the password.
     */
    String getPassword();

    /**
     * Indicates wether the user is activated.
     * @return Returns <code>true</code> if the user is activated, otherwise <code>false</code>.
     */
    boolean isActivated();

    /**
     * Gets the email
     * @return Returns the email string representation.
     */
    String getEmail();

    /**
     * Gets the list of all groups associated to the user.
     * @return Returns the list of all groups associated to the user.
     */
    Set<? extends IGroup> getGroups();

    /**
     * Gets indicating wether user is in group 
     * @param group 
     * @return return <code>true</code> if user is in group, otherwise <code>false</code>
     */
    boolean inGroup(IGroup group);

}