package org.cfr.commons.app;

import java.io.File;
import java.util.Date;
import java.util.Locale;

public interface IGlobalApplicationProperties {

    /**
     * The application key.
     *
     * @return an application key
     */
    String getKey();

    /**
     * Get the base URL of the current application.
     *
     * @return the current application's base URL
     */
    String getBaseUrl();

    /**
     * @return the displayable name of the application
     */
    String getDisplayName();

    /**
     * @return the version of the application
     */
    String getVersion();

    /**
     * @return the build date of the application
     */
    Date getBuildDate();

    /**
     * @return the build number of the application
     */
    String getBuildNumber();

    /**
     * @return the home directory of the application or null if none is defined
     */
    File getHomeDirectory();

    /**
     * 
     * @return
     */
    Locale getDefaultLocale();

    /**
     * 
     * @return
     */
    Environment getEnvironment();

}