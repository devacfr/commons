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
package org.cfr.commons.event.api;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.Aware;

import com.atlassian.event.api.EventPublisher;

/**
 * Interface to be implemented by beans that wish to be aware of their owning {@link EventPublisher}.
 * <p>
 * For example, beans can publish a event.
 * </p>
 * 
 * @author devacfr
 * @since 1.0
 */
public interface IEventPublisherAware extends Aware {

    /**
     * Callback that supplies the owning event publisher to a bean instance.
     * <p>
     * Invoked after the population of normal bean properties but before an initialization callback such as
     * {@link org.springframework.beans.factory.InitializingBean#afterPropertiesSet()} or a custom init-method.
     * 
     * @param eventPublisher
     *            owning BeanFactory (never <code>null</code>).
     */
    void setEventPublisher(@Nonnull final EventPublisher eventPublisher);
}
