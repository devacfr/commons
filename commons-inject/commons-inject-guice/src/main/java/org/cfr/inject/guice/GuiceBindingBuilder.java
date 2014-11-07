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
package org.cfr.inject.guice;

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.cfr.commons.util.Assert;
import org.cfr.inject.InjectionException;
import org.cfr.inject.binding.BindingBuilder;

import com.google.inject.Scopes;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.name.Names;

/**
 * @param <T>
 *
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public class GuiceBindingBuilder<T> implements BindingBuilder<T> {

    /**
     *
     */
    private final AnnotatedBindingBuilder<T> delagate;

    /**
     *
     * @param delagate
     */
    protected GuiceBindingBuilder(@Nonnull final AnnotatedBindingBuilder<T> delagate) {
        this.delagate = Assert.checkNotNull(delagate, "delagate");
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public @Nonnull BindingBuilder<T> annotatedWith(@Nonnull final Class<? extends Annotation> annotationType) {
        delagate.annotatedWith(annotationType);
        return this;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public @Nonnull BindingBuilder<T> annotatedWith(@Nonnull final Annotation annotation) {
        Annotation a = annotation;
        if (annotation instanceof Named) {
            a = Names.named(((Named) annotation).value());
        }
        delagate.annotatedWith(a);
        return this;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public @Nonnull BindingBuilder<T> to(@Nonnull final Class<? extends T> implementation) throws InjectionException {
        delagate.to(implementation);
        return this;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public @Nonnull BindingBuilder<T> toInstance(final T instance) throws InjectionException {
        delagate.toInstance(instance);
        return this;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public @Nonnull BindingBuilder<T> toProvider(final Class<? extends Provider<? extends T>> providerType)
            throws InjectionException {
        delagate.toProvider(providerType);
        return this;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public @Nonnull BindingBuilder<T> toProviderInstance(final Provider<? extends T> provider)
            throws InjectionException {
        // TODO Auto-generated method stub
        return this;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public @Nonnull BindingBuilder<T> in(final Class<? extends Annotation> scopeAnnotation) {
        delagate.in(scopeAnnotation);
        return this;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public @Nonnull BindingBuilder<T> in(final org.cfr.inject.Scope scope) {
        delagate.in(DI.guilify(scope));
        return this;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public @Nonnull BindingBuilder<T> inSingletonScope() {
        delagate.in(Singleton.class);
        return this;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public @Nonnull BindingBuilder<T> withoutScope() {
        delagate.in(Scopes.NO_SCOPE);
        return this;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public @Nonnull BindingBuilder<T> asEagerSingleton() {
        delagate.asEagerSingleton();
        return this;
    }

}
