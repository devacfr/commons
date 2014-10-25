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