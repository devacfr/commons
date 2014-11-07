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

import javax.annotation.Nonnull;

import org.cfr.inject.binding.BindingBuilder;
import org.cfr.inject.binding.ConstantBindingBuilder;

/**
 * <p>
 * The binder against which to register binding that will eventually be translated into the underlying implementation
 * component definition, when calling {@link #execute()}
 * </p>
 *
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public interface Binder {

    /**
     *
     * @param scopeAnnotation
     * @param scope
     */
    void bindScope(@Nonnull Class<? extends Annotation> scopeAnnotation, org.cfr.inject.Scope scope);

    /**
     * Starts an unnamed binding of a specific interface. Binding should continue using returned BindingBuilder.
     */
    @Nonnull
    <T> BindingBuilder<T> bind(@Nonnull Class<T> type);

    /**
     * Starts a binding of a specific interface based on a provided binding key. This method is more generic than
     * {@link #bind(Class)} and allows to create named bindings in addition to default ones. Binding should continue
     * using returned BindingBuilder.
     */
    @Nonnull
    <T> BindingBuilder<T> bind(@Nonnull Key<T> key);

    /**
     * Starts a binding of a constant value distinguished by its binding {@code name} parameter.
     * <p>
     * note: there is not conversion type, the value should be same type than method parameter or field of class.
     *
     * @param name
     *            the binding name associated to this constant.
     * @return Returns a new {@link ConstantBindingBuilder builder} instance allowing to associate a value.
     *
     */
    @Nonnull
    ConstantBindingBuilder bindConstant(@Nonnull String name);

    /**
     *
     * @param annotation
     * @return
     */
    @Nonnull
    ConstantBindingBuilder bindConstant(@Nonnull final Annotation annotation);

    /**
     *
     */
    void apply();

}