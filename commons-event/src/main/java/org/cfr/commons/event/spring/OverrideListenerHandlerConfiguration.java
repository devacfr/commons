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
package org.cfr.commons.event.spring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.cfr.commons.event.api.ISupportedListenerHandler;
import org.cfr.commons.util.Assert;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.config.ListenerHandlersConfiguration;
import com.atlassian.event.internal.AnnotatedMethodsListenerHandler;
import com.atlassian.event.spi.ListenerHandler;
import com.atlassian.event.spi.ListenerInvoker;
import com.google.common.collect.Lists;

/**
 * 
 * @author devacfr
 * @since 1.0
 */
public class OverrideListenerHandlerConfiguration implements ListenerHandlersConfiguration {

    @Override
    public List<ListenerHandler> getListenerHandlers() {
        return Lists.<ListenerHandler> newArrayList(new DecoratorAnnotatedMethodsListenerHandler());
    }

    /**
     * 
     * @return
     */
    public List<ISupportedListenerHandler> getSupportedListenerHandler() {
        return Lists.<ISupportedListenerHandler> newArrayList(new DecoratorAnnotatedMethodsListenerHandler());
    }

    /**
     * 
     * @author devacfr<christophefriederich@mac.com>
     *
     */
    public static class DecoratorAnnotatedMethodsListenerHandler implements ISupportedListenerHandler {

        /**
         * 
         */
        private final AnnotatedMethodsListenerHandler listenerHandler;

        /**
         * 
         */
        private final Class<? extends Annotation> annotationClass;

        /**
         * 
         */
        public DecoratorAnnotatedMethodsListenerHandler() {
            this(new AnnotatedMethodsListenerHandler());
        }

        /**
         * 
         * @param listenerHandler
         */
        public DecoratorAnnotatedMethodsListenerHandler(final AnnotatedMethodsListenerHandler listenerHandler) {
            this.listenerHandler = Assert.notNull(listenerHandler);
            this.annotationClass = EventListener.class;
        }

        @Override
        public List<? extends ListenerInvoker> getInvokers(final Object listener) {
            return listenerHandler.getInvokers(listener);
        }

        @Override
        public boolean supportsHandler(final Object listener) {
            for (Method method : listener.getClass().getMethods()) {
                if (isValidMethod(method)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 
         * @param method
         * @return
         */
        private boolean isValidMethod(final Method method) {
            if (isAnnotated(method)) {
                if (hasOneAndOnlyOneParameter(method)) {
                    return true;
                } else {
                    throw new RuntimeException("Method <" + method + "> of class <" + method.getDeclaringClass() + "> "
                            + "is annotated with <" + annotationClass.getName()
                            + "> but has 0 or more than 1 parameters! "
                            + "Listener methods MUST have 1 and only 1 parameter.");
                }
            }
            return false;
        }

        /**
         * 
         * @param method
         * @return
         */
        private boolean isAnnotated(final Method method) {
            return method.getAnnotation(annotationClass) != null;
        }

        /**
         * 
         * @param method
         * @return
         */
        private boolean hasOneAndOnlyOneParameter(final Method method) {
            return method.getParameterTypes().length == 1;
        }
    }
}