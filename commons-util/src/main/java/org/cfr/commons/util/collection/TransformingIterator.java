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

import static org.cfr.commons.util.Assert.notNull;

import java.util.Iterator;

import com.google.common.base.Function;

/**
 * {@link Iterator} implementation that decorates another {@link Iterator} who contains values of type I and uses a
 * {@link Function} that converts that I into a V.
 * <p>
 * This implementation is unmodifiable.
 * 
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 * @param <I>
 *            the value in the underlying iterator
 * @param <E>
 *            the value it is converted to
 */
class TransformingIterator<I, E> implements Iterator<E> {

    /** */
    private final Iterator<? extends I> iterator;

    /** */
    private final Function<I, E> decorator;

    TransformingIterator(final Iterator<? extends I> iterator, final Function<I, E> decorator) {
        notNull(iterator);
        notNull(decorator);
        this.iterator = iterator;
        this.decorator = decorator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public E next() {
        return decorator.apply(iterator.next());
    }

    @Override
    public void remove() {
        iterator.remove();
    }
}
