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

import org.cfr.commons.util.Assert;
import org.cfr.inject.binding.BindingBuilder;
import org.cfr.inject.binding.ConstantBindingBuilder;

/**
 * <p>
 * Extend this class to define your module.
 * </p>
 * <p>
 * It is recommended to make your module classes </strong>{@code final}</strong>, or to <strong>design</strong> them for
 * extensibility. If you're not sure which option to choose make them {@code final}
 * </p>
 * <p>
 * Here is a very basic sample module:
 * </p>
 *
 * <pre>
 * public final class MyModule extends AbstractModule {
 *     protected void configure() {
 *         bind(MyInterface.class).to(MyImplementation.class);
 *     }
 * }
 * </pre>
 *
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public abstract class AbstractModule implements Module {

    /**
     *
     */
    private Binder binder;

    @Override
    public final void configure(@Nonnull final Binder binder) {
        final Binder oldBinder = Assert.checkNotNull(binder, "binder");
        try {
            setBinder(binder);
            configure();
        } finally {
            this.binder = oldBinder;
        }
    }

    /**
     * Only here so we can test the configure method
     *
     * @param binder
     *            the binder to use with this module.
     */
    void setBinder(@Nonnull final Binder binder) {
        this.binder = Assert.checkNotNull(binder, "binder");
    }

    /**
     * Implement this method to register all the bindings you need for your module.
     */
    protected abstract void configure();

    /**
     *
     * @param scopeAnnotation
     * @param scope
     */
    public void bindScope(@Nonnull final Class<? extends Annotation> scopeAnnotation,
            @Nonnull final org.cfr.inject.Scope scope) {
        Assert.checkNotNull(scopeAnnotation, "scopeAnnotation");
        Assert.checkNotNull(scope, "scope");
        binder.bindScope(scopeAnnotation, scope);
    }

    /**
     * Starts an unnamed binding of a specific interface. Binding should continue using returned BindingBuilder.
     */
    public <T> BindingBuilder<T> bind(@Nonnull final Class<T> type) {
        return binder.bind(type);
    }

    /**
     * Starts a binding of a specific interface based on a provided binding key. This method is more generic than
     * {@link #bind(Class)} and allows to create named bindings in addition to default ones. Binding should continue
     * using returned BindingBuilder.
     */
    public @Nonnull <T> BindingBuilder<T> bind(@Nonnull final Key<T> key) {
        return binder.bind(Assert.checkNotNull(key, "key"));
    }

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
    public @Nonnull ConstantBindingBuilder bindConstant(@Nonnull final String name) {
        return binder.bindConstant(Assert.checkHasText(name, "name"));
    }

}