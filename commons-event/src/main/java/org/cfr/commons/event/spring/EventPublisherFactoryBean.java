package org.cfr.commons.event.spring;

import java.lang.annotation.Annotation;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import org.cfr.commons.event.api.IEventPublisherAware;
import org.cfr.commons.event.api.ISupportedListenerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.event.config.EventThreadPoolConfiguration;
import com.atlassian.event.internal.AsynchronousAbleEventDispatcher;
import com.atlassian.event.internal.DirectEventExecutorFactory;
import com.atlassian.event.internal.EventPublisherImpl;
import com.atlassian.event.internal.EventThreadPoolConfigurationImpl;
import com.atlassian.event.internal.LockFreeEventPublisher;
import com.atlassian.event.internal.UnboundedEventExecutorFactory;
import com.atlassian.event.spi.EventDispatcher;
import com.atlassian.event.spi.EventExecutorFactory;
import com.google.common.collect.ImmutableList;

/**
 * * {@link FactoryBean} that creates a named {@link EventPublisher} instance.
 *
 * <p>The event is annotated with the {@link com.atlassian.event.api.AsynchronousPreferred} annotation.</p>
 * @see com.atlassian.event.spi.ListenerHandler
 * @see EventDispatcher
 * @see com.atlassian.event.api.AsynchronousPreferred
 * @see com.atlassian.event.api.EventListener
 * @author devacfr
 * @since 1.0
 */
public class EventPublisherFactoryBean implements FactoryBean<EventPublisher>, InitializingBean, BeanDefinitionRegistryPostProcessor {

    /**
     * log instance.
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Specific {@link com.atlassian.event.config.ListenerHandlersConfiguration}.
     */
    protected OverrideListenerHandlerConfiguration listenerHandlers = new OverrideListenerHandlerConfiguration();

    /**
     * {@link EventPublisher} instance exposed by {@link FactoryBean} interface.
     */
    private EventPublisher eventPublisher;

    /**
     * indicate the synchronization between each event type dispatching.
     */
    private boolean blockingDispatch = true;

    /**
     * <code>true</code> indicate when an attempt to to queue a task 
     * will fail if no threads are immediately available to run it,
     * otherwise will cause new tasks to wait in the queue when all threads are busy.
     * Default is <code>true</code>.
     * @see java.util.concurrent.BlockingQueue
     * @see java.util.concurrent.LinkedBlockingQueue
     * @see java.util.concurrent.ThreadPoolExecutor
     * 
     */
    private boolean boundedQueue = true;

    /**
     * Execution management.
     * @see org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean
     */
    private ExecutorService executorService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        EventExecutorFactory executorFactory = null;
        if (this.executorService == null) {
            // Use default Thread pool configuration
            EventThreadPoolConfiguration threadPoolConfiguration = new EventThreadPoolConfigurationImpl();
            if (boundedQueue) {
                executorFactory = new DirectEventExecutorFactory(threadPoolConfiguration);
            } else {
                executorFactory = new UnboundedEventExecutorFactory(threadPoolConfiguration);
            }
        } else {
            executorFactory = new SpringEventExecutorFactory(this.executorService);
        }
        EventDispatcher eventDispatcher = new AsynchronousAbleEventDispatcher(executorFactory);
        if (!isBlockingDispatch()) {
            eventPublisher = new LockFreeEventPublisher(eventDispatcher, listenerHandlers);
        } else {
            eventPublisher = new EventPublisherImpl(eventDispatcher, listenerHandlers);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventPublisher getObject() throws Exception {
        return eventPublisher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getObjectType() {
        if (this.eventPublisher == null) {
            return EventPublisher.class;
        }
        return this.eventPublisher.getClass();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * 
     * @return Returns list of {@link ISupportedListenerHandler}.
     * If no listener handler exists returns empty list.
     * @see com.atlassian.event.spi.ListenerHandler
     */
    protected List<ISupportedListenerHandler> getListenerHandlers() {
        if (listenerHandlers == null) {
            return ImmutableList.of();
        }
        return listenerHandlers.getSupportedListenerHandler();
    }

    /**
     * Sets the indicating whether the event 'is' dispatched asynchronously or not. 
     * <p>Default is <code>true</code>.</p>
     * @param blocking allows dispatch event asynchronous or lock.
     * @see com.atlassian.event.api.AsynchronousPreferred
     */
    public void setBlockingDispactch(final boolean blocking) {
        this.blockingDispatch = blocking;
    }

    /**
     * Gets indicating whether the event 'is' dispatched asynchronously or not.
     * @return Returns <code>true</code> whether the event 'is' dispatched synchronously,
     * otherwise <code>false</code>.
     */
    public boolean isBlockingDispatch() {
        return blockingDispatch;
    }

    /**
     * Gets indicating whether the queuing an attempt to to queue a task 
     * will fail if no threads are immediately available to run it,
     * otherwise will cause new tasks to wait in the queue when all threads are busy.
     * <p>Note: this property is only applicable is the {@link #setExecutorService(ExecutorService)} doesn't filled.</p>
     * 
     * @return Returns <code>true</code> whether the queuing an attempt to to queue a task 
     * will fail if no threads are immediately available to run it,
     * otherwise returns <code>false</code>.
     */
    public boolean isBoundedQueue() {
        return boundedQueue;
    }

    /**
     * Sets indicating whether the queuing an attempt to to queue a task 
     * will fail if no threads are immediately available to run it,
     * otherwise will cause new tasks to wait in the queue when all threads are busy.
     * <p>Note: this property is only applicable is the {@link #setExecutorService(ExecutorService)} doesn't filled.</p>
     * @param boundedQueue bounded queue execution status.
     */
    public void setBoundedQueue(final boolean boundedQueue) {
        this.boundedQueue = boundedQueue;
    }

    /**
     * Sets the executor service.
     * @param executorService executor service
     */
    public void setExecutorService(final ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postProcessBeanDefinitionRegistry(final BeanDefinitionRegistry registry) {
        // not use
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) {
        // register Post processor
        beanFactory.addBeanPostProcessor(new EventBeanPostProcessor());
        beanFactory.addBeanPostProcessor(new EventListenerAwareProcessor(beanFactory));
    }

    /**
     * 
     * @author devacfr
     *
     */
    class EventBeanPostProcessor implements MergedBeanDefinitionPostProcessor, BeanFactoryAware {

        /**
         * 
         */
        private final Map<String, Boolean> singletonNames = new ConcurrentHashMap<String, Boolean>();

        /**
         * 
         */
        private BeanFactory beanFactory;

        /**
         * 
         */
        private final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<Class<? extends Annotation>>();

        /**
         * {@inheritDoc}
         */
        @Override
        public void setBeanFactory(final BeanFactory beanFactory) {
            this.beanFactory = beanFactory;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void postProcessMergedBeanDefinition(final RootBeanDefinition beanDefinition, final Class<?> beanType, final String beanName) {
            if (beanDefinition.isSingleton()) {
                this.singletonNames.put(beanName, Boolean.TRUE);
            }
        }

        /**
         * 
         */
        public EventBeanPostProcessor() {
            this.annotations.add(EventListener.class);
        }

        @Override
        public Object postProcessBeforeInitialization(final Object bean, final String beanName) {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(final Object bean, final String beanName) {
            if (isHandler(bean)) {
                // potentially not detected as a listener by getBeanNamesForType retrieval
                Boolean flag = this.singletonNames.get(beanName);
                if (Boolean.TRUE.equals(flag)) {
                    // singleton bean (top-level or inner): register on the fly
                    eventPublisher.register(bean);
                } else if (flag == null) {
                    if (log.isWarnEnabled() && !beanFactory.containsBean(beanName)) {
                        // inner bean with other scope - can't reliably process events
                        log.warn("Inner bean '" + beanName + "' implements ApplicationListener interface "
                                + "but is not reachable for event multicasting by its containing ApplicationContext "
                                + "because it does not have singleton scope. Only top-level listener beans are allowed "
                                + "to be of non-singleton scope.");
                    }
                    this.singletonNames.put(beanName, Boolean.FALSE);
                }
            }
            return bean;
        }

        /**
         * Gets the indicating whether the bean supports at one least {@link ISupportedListenerHandler}.
         * @param bean bean to test
         * @return Returns <code>true</code> whether the bean supports at one least {@link ISupportedListenerHandler},
         * otherwise returns <code>false</code>.
         */
        private boolean isHandler(final Object bean) {
            for (ISupportedListenerHandler handler : getListenerHandlers()) {
                try {
                    if (handler.supportsHandler(bean)) {
                        return true;
                    }
                } catch (RuntimeException ex) {
                    log.warn(ex.getMessage());
                }

            }
            return false;
        }

    }

    /**
      * {@link org.springframework.beans.factory.config.BeanPostProcessor}
      * implementation that passes the EventPublisher to beans that implement
      * the {@link EventListenerAwareProcessor} interface.
      *
      * @author devacfr
      *
      */
    class EventListenerAwareProcessor implements BeanPostProcessor {

        /**
         * Spring factory bean.
         */
        private final ConfigurableListableBeanFactory beanFactory;

        /**
         * Create a new ApplicationContextAwareProcessor for the given context.
         * @param applicationContext Spring factory bean
         */
        public EventListenerAwareProcessor(final ConfigurableListableBeanFactory applicationContext) {
            this.beanFactory = applicationContext;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object postProcessBeforeInitialization(final Object bean, final String beanName) {
            AccessControlContext acc = null;

            if (System.getSecurityManager() != null && bean instanceof IEventPublisherAware) {
                acc = this.beanFactory.getAccessControlContext();
            }

            if (acc != null) {
                AccessController.doPrivileged(new PrivilegedAction<Object>() {

                    @Override
                    public Object run() {
                        invokeAwareInterfaces(bean);
                        return null;
                    }
                }, acc);
            } else {
                invokeAwareInterfaces(bean);
            }

            return bean;
        }

        /**
         * Invoke {@link IEventPublisherAware#setEventPublisher(EventPublisher)} on bean implementing 
         * {@link IEventPublisherAware} interface.
         * @param bean Object  to invoke
         */
        private void invokeAwareInterfaces(final Object bean) {
            if (bean instanceof Aware) {
                if (bean instanceof IEventPublisherAware) {
                    ((IEventPublisherAware) bean).setEventPublisher(eventPublisher);
                }
            }
        }

        @Override
        public Object postProcessAfterInitialization(final Object bean, final String beanName) {
            return bean;
        }

    }
}