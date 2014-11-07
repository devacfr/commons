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

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;
import javax.inject.Provider;

import org.cfr.inject.InjectionException;
import org.cfr.inject.Injector;

/**
 * @param <T>
 *            An interface type of the service being bound.
 *
 *            A binding builder that helps with fluent binding creation.
 *
 * @since 1.0
 */
public interface BindingBuilder<T> {

    /**
     *
     * @param annotationType
     * @return
     */
    @Nonnull
    BindingBuilder<T> annotatedWith(@Nonnull final Class<? extends Annotation> annotationType);

    /**
     *
     * @param annotation
     * @return
     */
    @Nonnull
    BindingBuilder<T> annotatedWith(@Nonnull final Annotation annotation);

    /**
     *
     * @param implementation
     * @return
     * @throws InjectionException
     */
    @Nonnull
    BindingBuilder<T> to(@Nonnull Class<? extends T> implementation) throws InjectionException;

    /**
     *
     * @param instance
     * @return
     * @throws InjectionException
     */
    @Nonnull
    BindingBuilder<T> toInstance(@Nonnull T instance) throws InjectionException;

    /**
     *
     * @param providerType
     * @return
     * @throws InjectionException
     */
    @Nonnull
    BindingBuilder<T> toProvider(@Nonnull Class<? extends Provider<? extends T>> providerType)
            throws InjectionException;

    /**
     *
     * @param provider
     * @return
     * @throws InjectionException
     */
    @Nonnull
    BindingBuilder<T> toProviderInstance(@Nonnull Provider<? extends T> provider) throws InjectionException;

    /**
     *
     * @param scopeAnnotation
     * @return
     */
    @Nonnull
    BindingBuilder<T> in(@Nonnull Class<? extends Annotation> scopeAnnotation);

    /**
     * Sets the scope of a bound instance. This method is used to change the default scope which is usually a singleton
     * to a custom scope.
     */
    @Nonnull
    BindingBuilder<T> in(@Nonnull org.cfr.inject.Scope scope);

    /**
     * Sets the scope of a bound instance to singleton. Singleton is normally the default, so calling this method
     * explicitly is rarely needed.
     */
    @Nonnull
    BindingBuilder<T> inSingletonScope();

    /**
     * Sets the scope of a bound instance to "no scope". This means that a new instance of an object will be created on
     * every call to {@link Injector#getInstance(Class)}.
     */
    @Nonnull
    BindingBuilder<T> withoutScope();

    /**
     * Indicates to eagerly initialize this singleton-scoped binding upon cayenne startup.
     * <p>
     * <b>Note</b>
     * </p>
     * First, if there are problems with any of the singleton beans, exceptions will occur at cayenne startup time
     * versus at the time when the singleton may first be used. Secondly, as many singleton beans are resource manager
     * instances (like event manager or transaction managers) having the beans start with the initialization of the
     * container avoids any delay when the service provided by the resource manager bean is requested the first time.
     */
    @Nonnull
    BindingBuilder<T> asEagerSingleton();
}