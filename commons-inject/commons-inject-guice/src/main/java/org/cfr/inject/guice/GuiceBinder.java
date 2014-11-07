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

import org.cfr.commons.util.Assert;
import org.cfr.inject.Binder;
import org.cfr.inject.Key;
import org.cfr.inject.binding.BindingBuilder;
import org.cfr.inject.binding.ConstantBindingBuilder;

import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.name.Names;

/**
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public class GuiceBinder implements Binder {

    /**
     * The delagate Guice Buinder
     */
    private final com.google.inject.Binder delagate;

    /**
     * Default Contruct initialize with the Guice {@code Binder}
     *
     * @param delagate
     *            the Guice Binder
     */
    protected GuiceBinder(@Nonnull final com.google.inject.Binder delagate) {
        this.delagate = Assert.checkNotNull(delagate, "delagate");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindScope(final Class<? extends Annotation> scopeAnnotation, final org.cfr.inject.Scope scope) {
        this.delagate.bindScope(scopeAnnotation, DI.guilify(scope));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull <T> BindingBuilder<T> bind(@Nonnull final Class<T> type) {
        Assert.checkNotNull(type, "type");
        return new GuiceBindingBuilder<T>(this.delagate.bind(type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull <T> BindingBuilder<T> bind(@Nonnull final Key<T> key) {
        return new GuiceBindingBuilder<T>((AnnotatedBindingBuilder<T>) this.delagate.bind(DI.to(key)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull ConstantBindingBuilder bindConstant(@Nonnull final String name) {
        return new GuiceConstantBindingBuilder(this.delagate.bindConstant().annotatedWith(
                Names.named(Assert.checkHasText(name, "name"))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull ConstantBindingBuilder bindConstant(@Nonnull final Annotation annotation) {
        return new GuiceConstantBindingBuilder(this.delagate.bindConstant().annotatedWith(
                Assert.checkNotNull(annotation, "annotation")));
    }

    @Override
    public void apply() {
    }
}
