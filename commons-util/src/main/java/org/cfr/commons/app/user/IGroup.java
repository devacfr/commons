package org.cfr.commons.app.user;

/**
 * This interface represents the common group interface for the security.
 * @author cfriedri
 *
 */
public interface IGroup {

    /**
     * 
     * @return Returns name of group
     */
    public String getName();

    /**
     * 
     * @return Returns the description of group
     */
    public String getDescription();
}
