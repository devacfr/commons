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
     * @author devacfr
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