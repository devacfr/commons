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
package org.cfr.commons.event.api;

import com.atlassian.event.spi.ListenerHandler;

/**
 * Interface to be implemented by {@link ListenerHandler} that wish to be supported by this spring Atlassian event
 * publisher implementation.
 * 
 * @author devacfr
 * @since 1.0
 */
public interface ISupportedListenerHandler extends ListenerHandler {

    /**
     * Gets the indicating whethter the <code>listener</code> contains at least a method supported by this
     * {@link ListenerHandler}.
     * 
     * @param listener
     *            listener to check the support of this {@link ListenerHandler}.
     * @return Returns <code>true</code> whether the <code>listener</code> contains at least a method supported by this
     *         {@link ListenerHandler}, otherwise returns <code>false</code>.
     */
    boolean supportsHandler(Object listener);
}
