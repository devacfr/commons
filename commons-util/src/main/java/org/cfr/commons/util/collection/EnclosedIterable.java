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
import java.util.List;

import com.google.common.base.Function;

/**
 * A limited collection view that may be backed by the something that needs closing, for example a connection to a
 * database.
 * <p>
 * You can access all elements using the {@link #foreach(Consumer)} method.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 * @param <T>
 *            the type of element containing in this {@link EnclosedIterable}
 */
public interface EnclosedIterable<T> extends Sized {

    /**
     * Apply the sink to all elements in the Collection.
     */
    void foreach(Consumer<T> sink);

    /**
     * @return the likely size of the objects passed into the sink in {@link #foreach(Consumer)}. Be careful depending
     *         on this size being exact, as in many cases its best efforts value or may be stable due to concurrent
     *         changes.
     */
    @Override
    int size();

    /**
     * @return true if the there is no data behind it.
     */
    @Override
    boolean isEmpty();

    /**
     * Utility class for transforming a {@link EnclosedIterable} into a {@link List}. Generally you only want to do this
     * when the size of the iterable is small as it loads all the elements into memory.
     *
     * @param <T>
     *            the type of element containing in this {@link ListResolver}
     */
    class ListResolver<T> implements Resolver<EnclosedIterable<T>, List<T>> {

        /**
         * Get an {@link ArrayList} of the contents of the supplied {@link EnclosedIterable}
         *
         * @return a mutable {@link ArrayList} containing all elements of the iterable.
         */
        @Override
        public List<T> apply(final EnclosedIterable<T> iterable) {
            final List<T> result = new ArrayList<>();
            iterable.foreach(new Consumer<T>() {

                @Override
                public void consume(final T element) {
                    result.add(element);
                }
            });
            return result;
        }
    }

    /**
     * @author devacfr<christophefriederich@mac.com>
     */
    class Functions {

        /**
         * Pass all the elements of the iterable to the supplied {@link Consumer}. Guarantees that the iterator used
         * will be closed correctly
         *
         * @param iterable
         *            containing elements of type T
         * @param sink
         *            that will consume the elements
         * @param <T>
         *            the type of element containing in the iterable.
         */
        public static <T> void apply(final EnclosedIterable<T> iterable, final Consumer<T> sink) {
            iterable.foreach(sink);
        }

        /**
         * Get an {@link ArrayList} of the contents of the supplied {@link EnclosedIterable}
         *
         * @return a mutable {@link ArrayList} containing all elements of the iterable.
         * @param <T>
         *            the type of element containing in the iterable.
         */
        public static <T> List<T> toList(final EnclosedIterable<T> iterable) {
            return toList(iterable, new Function<T, T>() {

                @Override
                public T apply(final T input) {
                    return input;
                }
            });
        }

        /**
         * Get an {@link ArrayList} of the contents of the supplied {@link EnclosedIterable} transformed by the supplied
         * transform function into the new type O.
         *
         * @return a mutable {@link ArrayList} containing all elements of the iterable.
         * @param <I>
         *            the type of input object to transform from
         * @param <O>
         *            the type of output object that has been transform.
         */
        public static <I, O> List<O> toList(final EnclosedIterable<I> iterable, final Function<I, O> transformer) {
            final List<O> result = new ArrayList<>(iterable.size());
            iterable.foreach(new Consumer<I>() {

                @Override
                public void consume(final I element) {
                    result.add(transformer.apply(element));
                };
            });
            return result;
        }
    }
}
