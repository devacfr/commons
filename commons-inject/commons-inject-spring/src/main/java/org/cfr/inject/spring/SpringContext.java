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
    private final BeanDefinitionRegistry registry;

    public SpringContext(@Nonnull final BeanDefinitionRegistry registry) {
        this.registry = Assert.checkNotNull(registry, "registry");
    }

    public void add(final Binding<?> binding) {
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
        for (Class<?> ifce : binding.getInterfaces()) {
            registry.registerAlias(beanName, ifce.getName());
        }
    }

    private void setConstructorParameters(final Binding binding, final BeanDefinitionBuilder registrationBean) {
        for (Class<?> parameter : binding.getConstructorParameters()) {
            if (isConcreteClass(parameter)) {
                registrationBean.addConstructorArg(BeanDefinitionBuilder.rootBeanDefinition(parameter)
                        .getBeanDefinition());
            } else {
                registrationBean.addConstructorArgReference(parameter.getName());
            }
        }
    }

    private void setAutoWiringStrategy(final BeanDefinitionBuilder registrationBean) {
        registrationBean.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR);
    }

    private boolean isConcreteClass(final Class param) {
        return !param.isInterface();
    }

    private String getBeanDefinitionName(final Binding<?> binding) {
        return binding.getName() != null ? binding.getName() : binding.getInstanceClass().getName();
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
