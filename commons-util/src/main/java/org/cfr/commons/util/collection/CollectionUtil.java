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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class CollectionUtil {

    /**
     * Gets <code>true</code> if the supplied Collection is <code>null</code> or empty. Otherwise, return
     * <code>false</code>.
     *
     * @param collection
     *            the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * @param iterable
     * @return
     */
    public static <T> T first(final Iterable<T> iterable) {
        if (iterable != null) {
            Iterator<T> it = iterable.iterator();
            if (it.hasNext()) {
                return it.next();
            }
        }
        return null;
    }

    /**
     * @param array
     * @return
     */
    public static <T> T first(final T[] array) {
        if (array != null && array.length > 0) {
            return array[0];
        }
        return null;
    }

    /**
     * @param iterator
     * @param sink
     */
    public static <T> void foreach(final Iterator<T> iterator, final Consumer<T> sink) {
        while (iterator.hasNext()) {
            sink.consume(iterator.next());
        }
    }

    /**
     * @param iterable
     * @param sink
     */
    public static <T> void foreach(final Iterable<T> iterable, final Consumer<T> sink) {
        if (iterable != null) {
            foreach(iterable.iterator(), sink);
        }
    }

    /**
     * @param iterable
     * @return
     */
    public static <T> List<T> toList(final Iterable<T> iterable) {
        return toList(iterable.iterator());
    }

    /**
     * @param iterator
     * @return
     */
    public static <T> List<T> toList(final Iterator<T> iterator) {
        final List<T> result = new ArrayList<>();
        foreach(iterator, new Consumer<T>() {

            @Override
            public void consume(final T element) {
                if (element != null) {
                    result.add(element);
                }
            }
        });
        return result;
    }

    /**
     * Turn the enumeration into a list.
     *
     * @param <T>
     *            the element type
     * @param enumeration
     *            to enumerate over the elements
     * @return an unmodifiable {@link List} of the elements in the iterator
     */
    public static <T> List<T> toList(final Enumeration<? extends T> enumeration) {
        return toList(EnumerationIterator.fromEnumeration(enumeration));
    }

    /**
     * @param iterator
     * @param transformer
     * @return
     */
    public static <T, R> List<R> transform(final Iterator<T> iterator, final Function<T, R> transformer) {
        return toList(transformIterator(iterator, transformer));
    }

    /**
     * @param iterable
     * @param transformer
     * @return
     */
    public static <T, R> List<R> transform(final Iterable<T> iterable, final Function<T, R> transformer) {
        if (iterable == null) {
            return Collections.emptyList();
        }
        return transform(iterable.iterator(), transformer);
    }

    /**
     * @param iterator
     * @param transformer
     * @return
     */
    public static <T, R> Iterator<R> transformIterator(final Iterator<T> iterator, final Function<T, R> transformer) {
        return new TransformingIterator<>(iterator, transformer);
    }

    /**
     * Answers true if a predicate is true for at least one element of a collection.
     * <p>
     * A <code>null</code> collection or predicate returns false.
     *
     * @param collection
     *            the collection to get the input from, may be null
     * @param predicate
     *            the predicate to use, may be null
     * @return true if at least one element of the collection matches the predicate
     */
    public static <T> boolean exists(final Iterable<T> collection, final Predicate<? super T> predicate) {
        if (collection != null && predicate != null) {
            for (T t : collection) {
                if (predicate.apply(t)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Filter the iterable by applying a Predicate to each element. If the predicate returns false, remove the element.
     * If the input iterable or predicate is null, there is no change made.
     *
     * @param iterable
     *            the iterable to get the input from, may be null
     * @param predicate
     *            the predicate to use as a filter, may be null
     **/

    public static <E> void clean(final Iterable<E> iterable, final Predicate<? super E> predicate) {
        if (iterable != null && predicate != null) {
            for (Iterator<E> it = iterable.iterator(); it.hasNext();) {
                if (predicate.apply(it.next()) == false) {
                    it.remove();
                }
            }
        }
    }

    /**
     * Create filtered iterator by applying a Predicate to each element. If the input iterable or predicate is null,
     * there is no change made.
     *
     * @param iterator
     *            the iterator to get the input from, may be null
     * @param predicate
     *            the predicate to use as a filter, may be null
     */
    public static <T> Iterator<T> filter(final Iterator<T> iterator, final Predicate<T> predicate) {
        return new FilteredIterator<>(iterator, predicate);
    }

    /**
     * Create a filtered {@link Iterator}.
     *
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> filter(final Iterable<T> iterable, final Predicate<T> predicate) {
        return new FilteredIterable<>(iterable, predicate);
    }

    static class FilteredIterable<T> implements Iterable<T> {

        /** */
        private final Iterable<T> delegate;

        /** */
        private final Predicate<T> predicate;

        FilteredIterable(final Iterable<T> delegate, final Predicate<T> predicate) {
            this.delegate = delegate;
            this.predicate = predicate;
        }

        @Override
        public Iterator<T> iterator() {
            return new FilteredIterator<>(delegate.iterator(), predicate);
        }

        @Override
        public String toString() {
            return toList(this).toString();
        }
    }

    /**
     * @param collection
     *            a collection of element to sort.
     * @param comparator
     *            the comparator to determine the order of the list.
     * @return Returns new list sorted according to {@code comparator}.
     * @param <T>
     *            type of element containing in collection and to compare.
     */
    public static <T> List<T> sort(final Collection<T> collection, final Comparator<T> comparator) {
        final List<T> sorted = new ArrayList<>(collection);
        if (sorted.size() > 1) {
            Collections.sort(sorted, comparator);
        }
        return sorted;
    }
}
