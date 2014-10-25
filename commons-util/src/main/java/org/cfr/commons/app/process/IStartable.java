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
package org.cfr.commons.app.process;

/**
 * Implementing this interface allows Components to be notified of when the application has started.
 *
 * <p>
 * After the plugin system is initialised and components added to the dependency injection framework, then components
 * implementing this interface will have their {@link #start()} method called. Note that only plugin modules of type
 * Component will be considered as "Startable".
 */
public interface IStartable {

    /**
     * This method wil be called after the plugin system is fully initialised and all components added to the dependency
     * injection framework.
     *
     * @throws Exception
     *             Allows implementations to throw an Exception.
     */
    public void start() throws Exception;
}
