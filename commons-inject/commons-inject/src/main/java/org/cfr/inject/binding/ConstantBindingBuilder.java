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
package org.cfr.inject.binding;

import javax.annotation.Nonnull;

/**
 * @param <T>
 *            Represents the type of value to bind.
 *
 *            This builder allows binding constant value.
 *
 * @since 1.0
 */
public interface ConstantBindingBuilder {

    /**
     * Binds constant to the given String value.
     *
     * @param value
     *            constant value to bind.
     */
    void to(@Nonnull String value);

    /**
     * Binds constant to the given String value.
     *
     * @param value
     *            constant value to bind.
     */
    void to(@Nonnull int value);

    /**
     * Binds constant to the given String value.
     *
     * @param value
     *            constant value to bind.
     */
    void to(@Nonnull long value);

    /**
     * Binds constant to the given String value.
     *
     * @param value
     *            constant value to bind.
     */
    void to(@Nonnull boolean value);

    /**
     * Binds constant to the given String value.
     *
     * @param value
     *            constant value to bind.
     */
    void to(@Nonnull double value);

    /**
     * Binds constant to the given String value.
     *
     * @param value
     *            constant value to bind.
     */
    void to(@Nonnull float value);

    /**
     * Binds constant to the given String value.
     *
     * @param value
     *            constant value to bind.
     */
    void to(@Nonnull short value);

    /**
     * Binds constant to the given String value.
     *
     * @param value
     *            constant value to bind.
     */
    void to(@Nonnull char value);

    /**
     * Binds constant to the given String value.
     *
     * @param value
     *            constant value to bind.
     */
    void to(@Nonnull byte value);

    /**
     * Binds constant to the given String value.
     *
     * @param value
     *            constant value to bind.
     */
    void to(@Nonnull Class<?> value);

    /**
     *
     * @param value
     */
    <E extends Enum<E>> void to(E value);

}