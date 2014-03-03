package org.cfr.commons.event.api;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.Aware;

import com.atlassian.event.api.EventPublisher;

/**
 * Interface to be implemented by beans that wish to be aware of their
 * owning {@link EventPublisher}.
 * <p>For example, beans can publish a event.</p>
 * 
 * @author devacfr
 * @since 1.0
 */
public interface IEventPublisherAware extends Aware {

    /**
     * Callback that supplies the owning event publisher to a bean instance.
     * <p>Invoked after the population of normal bean properties
     * but before an initialization callback such as
     * {@link org.springframework.beans.factory.InitializingBean#afterPropertiesSet()} or a custom init-method.
     * @param eventPublisher owning BeanFactory (never <code>null</code>).
     */
    void setEventPublisher(@Nonnull final EventPublisher eventPublisher);
}
