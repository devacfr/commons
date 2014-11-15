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
package org.cfr.inject.guice.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.cfr.inject.InjectionException;

import com.google.common.collect.Lists;

/**
 * This class allows generate lifecycle metadata associated of a class. Metadata use common annotations
 * {@link PostConstruct} and {@link PreDestroy} of java specification request <a
 * href="http://en.wikipedia.org/wiki/JSR_250">JSR-250</a>.
 *
 * @author devacfr<christophefriederich@mac.com>
 *
 * @since 1.0
 * @see PostConstruct
 * @see PreDestroy
 */
public class LifecycleProcessor {

    /**
     * Contains all type of annotations on a method that needs to be executed after dependency injection is done.
     */
    private final Collection<Class<? extends Annotation>> initAnnotationTypes = new HashSet<Class<? extends Annotation>>(
            Arrays.asList(PostConstruct.class));

    /**
     * Contains all type of annotations on a method to signal that the instance is in the process of being removed by
     * the DI container.
     */
    private final Collection<Class<? extends Annotation>> preDestroyAnnotationTypes = new HashSet<Class<? extends Annotation>>(
            Arrays.asList(PreDestroy.class));

    /**
     * lifecycle metadata cache.
     */
    private transient Map<Class<?>, LifecycleMetadata> lifecycleMetadataCache = new ConcurrentHashMap<Class<?>, LifecycleMetadata>();

    public void clear() {
        this.lifecycleMetadataCache.clear();
    }

    /**
     * Creates or gets the lifecycle metadata associated to {@code clazz}. parameter.
     *
     * @param clazz
     *            the class
     * @return Returns the lifecycle metadata associated to {@code clazz} parameter (never {@code null}).
     */
    public LifecycleMetadata findLifecycleMetadata(final Class<?> clazz) {
        if (this.lifecycleMetadataCache == null) {
            // Happens after deserialization, during destruction...
            return buildLifecycleMetadata(clazz);
        }
        // Quick check on the concurrent map first, with minimal locking.
        LifecycleMetadata metadata = this.lifecycleMetadataCache.get(clazz);
        if (metadata == null) {
            synchronized (this.lifecycleMetadataCache) {
                metadata = this.lifecycleMetadataCache.get(clazz);
                if (metadata == null) {
                    metadata = buildLifecycleMetadata(clazz);
                    this.lifecycleMetadataCache.put(clazz, metadata);
                }
                return metadata;
            }
        }
        return metadata;
    }

    /**
     * Builds new instance {@link LifecycleMetadata} containing lifecycle metadata of class {@code clazz}.
     *
     * @param clazz
     *            the class containing metadata
     * @return Returns new instance {@link LifecycleMetadata} containing lifecycle (never {@code null}).
     */
    private LifecycleMetadata buildLifecycleMetadata(final Class<?> clazz) {
        final LinkedList<LifecycleElement> initMethods = Lists.newLinkedList();
        final LinkedList<LifecycleElement> preDestroyMethods = Lists.newLinkedList();
        final LinkedList<LifecycleElement> postDestroyMethods = Lists.newLinkedList();
        Class<?> targetClass = clazz;

        do {
            final LinkedList<LifecycleElement> currInitMethods = Lists.newLinkedList();
            final LinkedList<LifecycleElement> currPreDestroyMethods = Lists.newLinkedList();
            final LinkedList<LifecycleElement> currPostDestroyMethods = Lists.newLinkedList();
            for (final Method method : targetClass.getDeclaredMethods()) {
                if (this.initAnnotationTypes != null) {
                    for (final Class<? extends Annotation> annotation : initAnnotationTypes) {
                        if (method.getAnnotation(annotation) != null) {
                            final LifecycleElement element = new LifecycleElement(method);
                            currInitMethods.add(element);
                        }
                    }
                }
                if (this.preDestroyAnnotationTypes != null) {
                    for (final Class<? extends Annotation> annotation : this.preDestroyAnnotationTypes) {
                        if (method.getAnnotation(annotation) != null) {
                            currPreDestroyMethods.add(new LifecycleElement(method));
                        }
                    }
                }
            }
            initMethods.addAll(0, currInitMethods);
            preDestroyMethods.addAll(currPreDestroyMethods);
            postDestroyMethods.addAll(currPostDestroyMethods);
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);

        return new LifecycleMetadata(clazz, initMethods, preDestroyMethods, postDestroyMethods);
    }

    /**
     * This class contains all lifecycle methods of a class.
     *
     */
    public static class LifecycleMetadata {

        /**
         *
         */
        private final Set<LifecycleElement> initMethods;

        /**
         *
         */
        private final Set<LifecycleElement> preDestroyMethods;

        /**
         *
         * @param targetClass
         * @param initMethods
         * @param preDestroyMethods
         * @param postDestroyMethods
         */
        public LifecycleMetadata(final Class<?> targetClass, final Collection<LifecycleElement> initMethods,
                final Collection<LifecycleElement> preDestroyMethods,
                final Collection<LifecycleElement> postDestroyMethods) {

            this.initMethods = Collections.synchronizedSet(new LinkedHashSet<LifecycleElement>());
            for (final LifecycleElement element : initMethods) {
                this.initMethods.add(element);
            }

            this.preDestroyMethods = Collections.synchronizedSet(new LinkedHashSet<LifecycleElement>());
            for (final LifecycleElement element : preDestroyMethods) {
                this.preDestroyMethods.add(element);
            }
        }

        /**
         *
         * @return
         */
        public boolean isEmpty() {
            return this.initMethods.isEmpty() && this.preDestroyMethods.isEmpty();
        }

        /**
         *
         * @param target
         */
        public void invokeInitMethods(final Object target) {
            if (!this.initMethods.isEmpty()) {
                try {
                    for (final LifecycleElement element : this.initMethods) {
                        element.invoke(target);
                    }
                } catch (final InvocationTargetException ex) {
                    throw new InjectionException("Invocation of init method failed on bean'%s'.",
                            ex.getTargetException(), target.getClass());
                } catch (final Throwable ex) {
                    throw new InjectionException("Couldn't invoke init method on bean'%s'.", ex, target.getClass());
                }
            }
        }

        /**
         *
         * @param target
         */
        public void invokePreDestroyMethods(final Object target) {
            if (!this.preDestroyMethods.isEmpty()) {
                try {
                    for (final LifecycleElement element : this.preDestroyMethods) {
                        element.invoke(target);
                    }
                } catch (final InvocationTargetException ex) {
                    final String msg = "Invocation of destroy method failed on bean '" + target.getClass() + "'";
                    // TODO add logger
                } catch (final Throwable ex) {
                    // noop
                }

            }
        }

    }

    /**
     * Class representing injection information about an annotated method.
     */
    private static class LifecycleElement {

        /**
         *
         */
        private final Method method;

        /**
         *
         */
        private final String identifier;

        /**
         *
         * @param method
         */
        public LifecycleElement(final Method method) {
            if (method.getParameterTypes().length != 0) {
                throw new IllegalStateException("Lifecycle method annotation requires a no-arg method: " + method);
            }
            this.method = method;
            this.identifier = (Modifier.isPrivate(method.getModifiers()) ? method.getDeclaringClass() + "."
                    + method.getName() : method.getName());
        }

        /**
         *
         * @param target
         * @throws Throwable
         */
        public void invoke(final Object target) throws Throwable {
            makeAccessible(this.method);
            this.method.invoke(target, (Object[]) null);
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LifecycleElement)) {
                return false;
            }
            final LifecycleElement otherElement = (LifecycleElement) other;
            return (this.identifier.equals(otherElement.identifier));
        }

        @Override
        public int hashCode() {
            return this.identifier.hashCode();
        }

        /**
         *
         * @param method
         */
        public static void makeAccessible(final Method method) {
            if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass()
                    .getModifiers())) && !method.isAccessible()) {
                method.setAccessible(true);
            }
        }
    }
}