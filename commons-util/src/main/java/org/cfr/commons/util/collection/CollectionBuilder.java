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

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.Collections.unmodifiableSortedSet;
import static org.cfr.commons.util.Assert.notNull;
import static org.cfr.commons.util.collection.CollectionUtil.toList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

/**
 * Convenience class for creating collections ({@link Set} and {@link List}) instances or {@link EnclosedIterable
 * enclosed iterables}.
 * <p>
 * The default methods {@link #asList()} and {@link #asSet()} and {@link #asSortedSet()} create immutable collections.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 * @param <T>
 *            contained in the created collections.
 */
public final class CollectionBuilder<T> {

    /**
     *
     */
    private static final Ordering<?> NATURAL_ORDER = new NaturalOrdering();

    /**
     *
     */
    private final List<T> elements = Lists.<T> newArrayList();

    CollectionBuilder(final Collection<? extends T> initialElements) {
        elements.addAll(initialElements);
    }

    /**
     * Appends the specified element to this list .
     *
     * @param element
     *            element to add
     * @return Returns fluent instance.
     */
    public CollectionBuilder<T> add(final T element) {
        elements.add(element);
        return this;
    }

    /**
     * Create new fluent collection builder.
     *
     * @return Returns new instance of {@link CollectionBuilder}.
     * @param <T>
     *            contained in the created collections.
     */
    public static <T> CollectionBuilder<T> newBuilder() {
        return new CollectionBuilder<>(Collections.<T> emptyList());
    }

    /**
     * @param elements
     * @return
     * @param <T>
     *            contained in the created collections.
     */
    @SafeVarargs
    public static <T> CollectionBuilder<T> newBuilder(final T... elements) {
        return new CollectionBuilder<>(Arrays.asList(elements));
    }

    /**
     * @param elements
     * @return
     * @param <T>
     *            contained in the created collections.
     */
    public static <T> CollectionBuilder<T> newBuilder(final List<T> elements) {
        return new CollectionBuilder<>(elements);
    }

    /**
     * @param elements
     * @return
     * @param <T>
     *            contained in the created collections.
     */
    @SafeVarargs
    public static <T> List<T> list(final T... elements) {
        return unmodifiableList(Arrays.asList(elements));
    }

    /**
     * @return
     * @param <T>
     *            contained in the created collections.
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<T> natural() {
        return (Comparator<T>) NATURAL_ORDER;
    }

    /**
     * @param elements
     * @return
     */
    @SafeVarargs
    public final <E extends T> CollectionBuilder<T> addAll(final E... elements) {
        this.elements.addAll(Arrays.asList(notNull(elements, "elements")));
        return this;
    }

    /**
     * @param elements
     * @return
     */
    public CollectionBuilder<T> addAll(final Collection<? extends T> elements) {
        this.elements.addAll(notNull(elements, "elements"));
        return this;
    }

    /**
     * @param elements
     * @return
     */
    public CollectionBuilder<T> addAll(final Enumeration<? extends T> elements) {
        this.elements.addAll(toList(notNull(elements, "elements")));
        return this;
    }

    /**
     * @return
     */
    public Collection<T> asCollection() {
        return asList();
    }

    /**
     * @return
     */
    public Collection<T> asMutableCollection() {
        return asMutableList();
    }

    /**
     * @return
     */
    public List<T> asArrayList() {
        return Lists.newArrayList(elements);
    }

    /**
     * @return
     */
    public List<T> asLinkedList() {
        return Lists.newLinkedList(elements);
    }

    /**
     * @return
     */
    public List<T> asList() {
        return unmodifiableList(new ArrayList<>(elements));
    }

    /**
     * @return
     */
    public List<T> asMutableList() {
        return asArrayList();
    }

    /**
     * @return
     */
    public Set<T> asHashSet() {
        return Sets.newHashSet(elements);
    }

    /**
     * @return
     */
    public Set<T> asListOrderedSet() {
        return Sets.newLinkedHashSet(elements);
    }

    /**
     * @return
     */
    public Set<T> asImmutableListOrderedSet() {
        return unmodifiableSet(new LinkedHashSet<>(elements));
    }

    /**
     * @return
     */
    public Set<T> asSet() {
        return unmodifiableSet(new HashSet<>(elements));
    }

    /**
     * @return
     */
    public Set<T> asMutableSet() {
        return asHashSet();
    }

    /**
     * @return
     */
    public SortedSet<T> asTreeSet() {
        return new TreeSet<>(elements);
    }

    /**
     * Return a {@link SortedSet} of the elements of this builder in their natural order. Note, will throw an exception
     * if the elements are not comparable.
     *
     * @return an immutable sorted set.
     * @throws ClassCastException
     *             if the elements do not implement {@link Comparable}.
     */
    public SortedSet<T> asSortedSet() {
        return unmodifiableSortedSet(new TreeSet<>(elements));
    }

    /**
     * @param comparator
     * @return
     */
    public SortedSet<T> asSortedSet(final Comparator<? super T> comparator) {
        final SortedSet<T> result = new TreeSet<>(comparator);
        result.addAll(elements);
        return unmodifiableSortedSet(result);
    }

    /**
     * @return
     */
    public SortedSet<T> asMutableSortedSet() {
        return asTreeSet();
    }

    /**
     * @return
     */
    public EnclosedIterable<T> asEnclosedIterable() {
        return CollectionEnclosedIterable.copy(elements);
    }

    static class NaturalOrdering extends Ordering<Comparable<Object>> implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = 0;

        @Override
        @SuppressWarnings("PMD.CompareObjectsWithEquals")
        public int compare(final Comparable<Object> left, final Comparable<Object> right) {
            notNull(right, "right"); // left null is caught later
            if (left == right) {
                return 0;
            }

            return left.compareTo(right);
        }

        // preserving singleton-ness gives equals()/hashCode() for free
        private Object readResolve() {
            return NATURAL_ORDER;
        }

        @Override
        public String toString() {
            return "Ordering.natural()";
        }

    }
}
