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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Simple collection based {@link EnclosedIterable}.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 * @param <T>
 *            the type of element containing in this {@link CollectionEnclosedIterable}
 */
public class CollectionEnclosedIterable<T> implements EnclosedIterable<T> {

    /**
     *
     */
    private final Collection<? extends T> collection;

    CollectionEnclosedIterable(final Collection<? extends T> collection) {
        this.collection = notNull(collection, "collection");
    }

    /**
     * Create an {@link EnclosedIterable} from the supplied Collection. Does not copy the collection so you should only
     * use this if you are about to lose the reference or the collection is immutable.
     *
     * @param collection
     * @return
     * @param <T>
     *            the type of element containing in this {@link CollectionEnclosedIterable}
     */
    public static <T> EnclosedIterable<T> from(final Collection<? extends T> collection) {
        return new CollectionEnclosedIterable<>(collection);
    }

    /**
     * @param collection
     * @return
     * @param <T>
     *            the type of element containing in this {@link CollectionEnclosedIterable}
     */
    public static <T> EnclosedIterable<T> copy(final Collection<? extends T> collection) {
        return new CollectionEnclosedIterable<>(new ArrayList<>(collection));
    }

    @Override
    public void foreach(final Consumer<T> sink) {
        for (final T element : collection) {
            sink.consume(element);
        }
    };

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final CollectionEnclosedIterable<T> other = (CollectionEnclosedIterable<T>) obj;
        return collection.equals(other.collection);
    }

    @Override
    public int hashCode() {
        return collection.hashCode();
    }
}
