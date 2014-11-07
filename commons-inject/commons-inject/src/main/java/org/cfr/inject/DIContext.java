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
package org.cfr.inject;

import org.cfr.inject.binding.Binding;

/**
 * This interface is the link between the module definitions (and their bindings) and the actual DI context used by the
 * application.
 *
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public interface DIContext {

    /**
     * Adds the given binding to the underlying DI context. I.e it will create the appropriate bean definition(s).
     *
     * @param binding
     *            the {@link Binding binding} to register with the container
     */
    void add(Binding<?> binding);
}
