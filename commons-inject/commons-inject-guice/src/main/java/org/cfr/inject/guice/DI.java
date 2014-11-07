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
import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.inject.Named;

import org.cfr.commons.util.Assert;
import org.cfr.commons.util.collection.CollectionUtil;
import org.cfr.inject.Injector;
import org.cfr.inject.Key;
import org.cfr.inject.Module;

import com.google.common.base.Function;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.Stage;
import com.google.inject.name.Names;
import com.google.inject.util.Providers;

/**
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public final class DI {

    /**
     *
     */
    private DI() {
    }

    /**
     *
     * @param modules
     * @return
     */
    public static Injector createInjector(final Module... modules) {
        return createInjector(Arrays.asList(modules));
    }

    /**
     *
     * @param modules
     * @return
     */
    public static Injector createInjector(final Iterable<Module> modules) {
        return new GuiceInjector(Guice.createInjector(CollectionUtil.transform(modules,
                new Function<Module, com.google.inject.Module>() {

                    @Override
                    public com.google.inject.Module apply(final Module model) {
                        return DI.guicify(model);
                    }

                })));
    }

    public static Injector createInjector(final Stage stage, final Module... modules) {
        return createInjector(stage, Arrays.asList(modules));
    }

    public static Injector createInjector(final Stage stage, final Iterable<Module> modules) {
        return new GuiceInjector(Guice.createInjector(stage,
                CollectionUtil.transform(modules, new Function<Module, com.google.inject.Module>() {

                    @Override
                    public com.google.inject.Module apply(final Module model) {
                        return DI.guicify(model);
                    }

                })));
    }

    /**
     *
     * @param scope
     * @return
     */
    public static @Nonnull Scope guilify(@Nonnull final org.cfr.inject.Scope scope) {
        Assert.notNull(scope);
        return new com.google.inject.Scope() {

            @Override
            public <T> Provider<T> scope(final com.google.inject.Key<T> key, final Provider<T> unscoped) {
                return Providers.guicify(scope.scope(to(key), unscoped));
            }
        };
    }

    public static @Nonnull org.cfr.inject.Scope normalize(@Nonnull final Scope scope) {
        Assert.notNull(scope);
        return new org.cfr.inject.Scope() {

            @Override
            public <T> javax.inject.Provider<T> scope(final Key<T> key, final javax.inject.Provider<T> unscoped) {
                return scope.scope(to(key), Providers.guicify(unscoped));
            }

        };
    }

    /**
     *
     * @param key
     * @return
     */
    public static <T> com.google.inject.Key<T> to(@Nonnull final Key<T> key) {
        Assert.notNull(key);
        if (key.getAnnotation() != null) {
            Annotation annotation = key.getAnnotation();
            if (annotation instanceof Named) {
                annotation = Names.named(((Named) annotation).value());
            }
            return com.google.inject.Key.get(key.getType(), annotation);
        } else if (key.getAnnotationType() != null) {
            return com.google.inject.Key.get(key.getType(), key.getAnnotationType());
        } else {
            return com.google.inject.Key.get(key.getType());
        }
    }

    /**
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Key<T> to(@Nonnull final com.google.inject.Key<T> key) {
        Assert.notNull(key);
        key.getAnnotation();
        return Key.get((Class<T>) key.getTypeLiteral().getRawType(), key.getAnnotationType());

    }

    /**
     * @param model
     * @return
     */
    public static com.google.inject.Module guicify(final Module model) {
        return new com.google.inject.Module() {

            @Override
            public void configure(final Binder binder) {
                model.configure(new GuiceBinder(binder));
            }

        };
    }
}
