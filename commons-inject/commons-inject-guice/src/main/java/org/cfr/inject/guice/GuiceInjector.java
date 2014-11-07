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
import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Provider;

import org.cfr.commons.util.Assert;
import org.cfr.inject.InjectionException;
import org.cfr.inject.Injector;
import org.cfr.inject.Key;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

/**
 * @author devacfr<christophefriederich@mac.com>
 *
 */
public class GuiceInjector implements Injector {

    /**
     *
     */
    private @Nonnull final com.google.inject.Injector delegate;

    /**
     *
     * @param delagate
     */
    public GuiceInjector(@Nonnull final com.google.inject.Injector delagate) {
        this.delegate = Assert.checkNotNull(delagate, "delagate");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getInstance(@Nonnull final Class<T> type) throws InjectionException {
        return delegate.getInstance(Assert.checkNotNull(type, "type"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getInstance(@Nonnull final Key<T> key) throws InjectionException {
        return delegate.getInstance(DI.to(Assert.checkNotNull(key, "key")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Provider<T> getProvider(@Nonnull final Class<T> type) throws InjectionException {
        return delegate.getProvider(Assert.checkNotNull(type, "type"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Provider<T> getProvider(@Nonnull final Key<T> key) throws InjectionException {
        return delegate.getProvider(DI.to(Assert.checkNotNull(key, "key")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void injectMembers(@Nonnull final Object object) {
        delegate.injectMembers(Assert.checkNotNull(object, "object"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Class<? extends Annotation>, org.cfr.inject.Scope> getScopeBindings() {
        return Maps.transformValues(delegate.getScopeBindings(),
                new Function<com.google.inject.Scope, org.cfr.inject.Scope>() {

                    @Override
                    public org.cfr.inject.Scope apply(@Nonnull final com.google.inject.Scope scope) {
                        return DI.normalize(scope);
                    }
                });
    }
}
