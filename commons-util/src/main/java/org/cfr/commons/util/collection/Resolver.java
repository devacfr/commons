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
package org.cfr.commons.util.collection;

import com.google.common.base.Function;

/**
 * A interface to resolve some input object into an output object.
 * <p/>
 * Semantically, this could be a Factory, Generator, Builder, Closure, Transformer, Decorator or something else
 * entirely. No guarantees are implied by this interface. Specifically, input and output objects may or may not be
 * nullable, runtime exceptions may or may not be thrown and the method may or may not block.
 *
 * @since 1.0
 */
public interface Resolver<I, O> extends Function<I, O> {

    /**
     * Resolve an instance of the output type from an object of the input type.
     *
     * @param input
     *            the input object to resolve from.
     * @return the output object that has been resolved.
     */
    @Override
    O apply(I input);
}
