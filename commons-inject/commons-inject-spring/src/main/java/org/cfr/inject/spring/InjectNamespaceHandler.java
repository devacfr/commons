package org.cfr.inject.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class InjectNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("modules", new ModulesBeanDefinitionParser());
    }
}
