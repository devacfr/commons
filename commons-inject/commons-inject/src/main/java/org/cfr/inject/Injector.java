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
import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Provider;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 */
public interface Injector {

    /**
     * Gets a instance bound in the container for a specific type
     *
     * @param type
     * @return Returns a instance bound in the container for a specific type.
     * @throws InjectionException occurs if the type is not bound, or an instance can not be created.
     *
     * @param <T>
     */
    @Nonnull
    <T> T getInstance(@Nonnull Class<T> type) throws InjectionException;

    /**
     * Gets a instance bound in the container for a specific binding key.
     *
     * @param key
     * @return Returns a instance bound in the container for a specific binding key.
     * @throws InjectionException occurs if the key is not bound, or an instance can not be created.
     *
     * @param <T>
     */
    @Nonnull
    <T> T getInstance(Key<T> key) throws InjectionException;

    /**
     * Gets the provider used to obtain the instance for the given type.
     *
     * @param type the type of class associated to returned {@link Provider}.
     * @return Returns the {@link javax.inject.Provider} for the given type. Avoid using directly this method,
     * prefer the dependency injection.
     * @throws InjectionException occurs if the binding for the type of class doesn't exist.
     *
     * @param <T>
     */
    @Nonnull
    <T> Provider<T> getProvider(Class<T> type) throws InjectionException;

    /**
     * Gets the provider used to obtain the instance for the given binding key.
     *
     * @param key the binding identifier associated to returned provider.
     * @return Returns the {@link javax.inject.Provider} for the given binding key. Avoid using directly this method,
     * prefer the dependency injection.
     * @throws InjectionException occurs if the binding for the type of class doesn't exist.
     *
     * @param <T>
     */
    @Nonnull
    <T> Provider<T> getProvider(@Nonnull Key<T> key) throws InjectionException;

    /**
     * Performs field injection on a given object, ignoring constructor injection. Since Cayenne DI injector returns
     * fully injected objects, this method is rarely used directly.
     * <p>
     * <b>Note</b>: that using this method inside a custom DI {@link Provider} will most
     * likely result in double injection, as custom provider is wrapped in a
     * field-injecting provider by the DI container. Instead custom providers
     * must initialize object properties manually, obtaining dependencies from
     * Injector.
     * </p>
     */
    void injectMembers(@Nonnull Object object);

    /**
     * Gets a map containing all scopes in the injector.
     *
     * @return Returns a unmodifiable map containing all scopes in the injector.
     */
    @Nonnull
    Map<Class<? extends Annotation>, org.cfr.inject.Scope> getScopeBindings();

}