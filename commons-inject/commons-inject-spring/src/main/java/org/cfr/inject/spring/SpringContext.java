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
package org.cfr.inject.spring;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.cfr.commons.util.Assert;
import org.cfr.inject.DIContext;
import org.cfr.inject.binding.Binding;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * Implementation of the {@link DIContext} that knows how to create Spring
 * {@link org.springframework.beans.factory.config.BeanDefinition bean definitions} from {@link Binding bindings}
 */
final class SpringContext implements DIContext {

    /**
     *
     */
    private final @Nonnull BeanDefinitionRegistry registry;

    public SpringContext(@Nonnull final BeanDefinitionRegistry registry) {
        this.registry = Assert.checkNotNull(registry, "registry");
    }

    @Override
    public void add(final @Nonnull Binding<?> binding) {
        Assert.checkNotNull(binding, "binding");
        final BeanDefinitionBuilder registrationBean = BeanDefinitionBuilder.rootBeanDefinition(binding
                .getInstanceClass());

        if (binding.hasConstructorSpecified()) {
            setConstructorParameters(binding, registrationBean);
        } else {
            setAutoWiringStrategy(registrationBean);
        }

        final String beanName = getBeanDefinitionName(binding);
        final BeanDefinition beanDefinition = registrationBean.getBeanDefinition();
        registry.registerBeanDefinition(beanName, beanDefinition);
        // for (Class<?> ifce : binding.getInterfaces()) {
        // registry.registerAlias(beanName, ifce.getName());
        // }
    }

    private void setConstructorParameters(@Nonnull final Binding<?> binding,
            @Nonnull final BeanDefinitionBuilder registrationBean) {
        for (Class<?> parameter : binding.getConstructorParameters()) {
            if (isConcreteClass(parameter)) {
                registrationBean.addConstructorArgValue(BeanDefinitionBuilder.rootBeanDefinition(parameter)
                        .getBeanDefinition());
            } else {
                registrationBean.addConstructorArgReference(parameter.getName());
            }
        }
    }

    private void setAutoWiringStrategy(@Nonnull final BeanDefinitionBuilder registrationBean) {
        registrationBean.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR);
    }

    private boolean isConcreteClass(final Class<?> param) {
        return !param.isInterface();
    }

    private @Nonnull String getBeanDefinitionName(final @Nonnull Binding<?> binding) {
        String name = binding.getKey().getName();
        if (name != null) {
        } else if (binding.getTargetKey() != null) {
            name = binding.getTargetKey().getType().getName();
        } else {
            name = binding.getKey().getType().getName();
        }
        return name;
    }

    private static @Nonnull Iterable<String> getInterfacesAsString(@Nonnull final Iterable<Class<?>> interfaces) {
        Assert.checkNotNull(interfaces, "interfaces");
        final Collection<String> interfacesAsString = new ArrayList<String>();
        for (Class<?> ifce : interfaces) {
            interfacesAsString.add(ifce.getName());
        }
        return interfacesAsString;
    }
}
