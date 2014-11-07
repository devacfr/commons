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

import java.lang.annotation.Annotation;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.cfr.commons.util.Assert;

import com.google.common.base.MoreObjects;

/**
 * @param <T>
 *
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public final class Key<T> {

    /**
     *
     */
    private @Nonnull final Class<T> type;

    /**
     *
     */
    private @Nullable final Class<? extends Annotation> annotationType;

    /**
     *
     */
    private @Nullable final Annotation annotation;

    /**
     * Creates a key for a nameless binding of a given type.
     * 
     * @param type
     * @return Returns a new key instance for a nameless binding of a given type.
     *
     * @param <T>
     * @since 1.0
     */
    public static @Nonnull <T> Key<T> get(@Nonnull final Class<T> type) {
        return new Key<T>(type, (Annotation) null);
    }

    /**
     * Creates a key for a named binding of a given type. 'bindingName' that is an empty String is treated the same way
     * as a null 'bindingName'. In both cases a nameless binding key is created.
     * 
     * @param type
     * @return a new key for a named binding of a given type.
     *
     * @param <T>
     * @since 1.0
     */
    public static <T> Key<T> get(@Nonnull final Class<T> type, @Nullable final Class<? extends Annotation> annotation) {
        return new Key<T>(type, annotation);
    }

    public static <T> Key<T> get(@Nonnull final Class<T> type, final Annotation annotation) {
        return new Key<T>(type, annotation);
    }

    protected Key(@Nonnull final Class<T> type, @Nullable final Annotation annotation) {
        Assert.checkNotNull(type, "type");
        this.type = type;
        if (annotation != null) {
            this.annotationType = annotation.annotationType();
            this.annotation = annotation;
        } else {
            this.annotation = null;
            this.annotationType = null;
        }
    }

    protected Key(@Nonnull final Class<T> type, @Nullable final Class<? extends Annotation> annotation) {
        Assert.notNull(type, "type is required");
        this.type = type;
        this.annotationType = annotation;
        this.annotation = null;
    }

    public Class<T> getType() {
        return type;
    }

    public Class<? extends Annotation> getAnnotationType() {
        return annotationType;
    }

    public final Annotation getAnnotation() {
        return annotation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable final Object object) {
        if (object == this) {
            return true;
        }

        if (object instanceof Key<?>) {
            final Key<?> key = (Key<?>) object;
            return type.equals(key.type) && Objects.equals(annotationType, key.annotationType)
                    && Objects.equals(annotation, key.annotation);
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, annotationType, annotation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).add("type", type).add("annotationType", this.annotationType)
                .add("annotation", this.annotation).toString();

    }
}