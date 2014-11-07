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
package org.cfr.inject.internal;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.cfr.commons.util.Assert;
import org.cfr.inject.Binder;
import org.cfr.inject.DIContext;
import org.cfr.inject.Key;
import org.cfr.inject.Scope;
import org.cfr.inject.binding.Binding;
import org.cfr.inject.binding.BindingBuilder;
import org.cfr.inject.binding.ConstantBindingBuilder;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public class DefaultBinder implements Binder {

    /**
     *
     */
    @Nonnull
    private final DIContext context;

    /**
     *
     * @param context
     */
    public DefaultBinder(@Nonnull final DIContext context) {
        this.context = Assert.checkNotNull(context, "context");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindScope(final Class<? extends Annotation> scopeAnnotation, final Scope scope) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> BindingBuilder<T> bind(final Class<T> type) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> BindingBuilder<T> bind(final Key<T> key) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConstantBindingBuilder bindConstant(final String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConstantBindingBuilder bindConstant(final Annotation annotation) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        registerBindingWithDIContext(Collections.<Binding<?>> emptyList());

    }

    /**
     *
     * @param bindings
     */
    private void registerBindingWithDIContext(final List<Binding<?>> bindings) {
        for (final Binding<?> binding : bindings) {
            context.add(binding);
        }
    }
}
