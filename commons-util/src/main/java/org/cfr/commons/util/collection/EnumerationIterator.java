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

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Adaptor for turning an {@link Enumeration} into an {@link Iterator}.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 * @param <E>
 *            the type of element produced.
 */
public class EnumerationIterator<E> implements Iterator<E> {

    /**
     *
     */
    private final Enumeration<? extends E> enumeration;

    EnumerationIterator(final Enumeration<? extends E> enumeration) {
        this.enumeration = enumeration;
    }

    /**
     * @param enumeration
     * @return
     */
    public static <E> Iterator<E> fromEnumeration(final Enumeration<? extends E> enumeration) {
        return new EnumerationIterator<>(enumeration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E next() {
        return enumeration.nextElement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
