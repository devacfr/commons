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
package org.cfr.commons.app;

import java.io.File;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Nonnull;

/**
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public interface IGlobalApplicationProperties {

    /**
     * Gets the application key.
     *
     * @return an application key.
     */
    @Nonnull
    String getKey();

    /**
     * Gets the base URL of the current application.
     *
     * @return Returns the current application's base URL.
     */
    @Nonnull
    String getBaseUrl();

    /**
     * Gets the displayable name of the application.
     *
     * @return Returns the displayable name of the application.
     */
    @Nonnull
    String getDisplayName();

    /**
     * Gets the version of the application.
     *
     * @return Returns the version of the application.
     */
    @Nonnull
    String getVersion();

    /**
     * Gets the build date of the application.
     *
     * @return Returns the build date of the application.
     */
    @Nonnull
    Date getBuildDate();

    /**
     * Gets the build number of the application.
     *
     * @return the build number of the application.
     */
    @Nonnull
    String getBuildNumber();

    /**
     * Gets the home directory of the application or null if none is defined.
     *
     * @return Returns the home directory of the application or null if none is defined.
     */
    @Nonnull
    File getHomeDirectory();

    /**
     * Gets the current value of the default locale for the application.
     * 
     * @return Returns the current value of the default locale for the application.
     */
    @Nonnull
    Locale getDefaultLocale();

    /**
     * Gets the current selected environment for the application.
     * 
     * @return Returns the current selected environment for the application.
     */
    @Nonnull
    Environment getEnvironment();

}