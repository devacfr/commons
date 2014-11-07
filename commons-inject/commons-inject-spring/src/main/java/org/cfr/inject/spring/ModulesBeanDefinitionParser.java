package org.cfr.inject.spring;

import org.cfr.inject.AbstractModule;
import org.cfr.inject.Binder;
import org.cfr.inject.internal.DefaultBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.ClassUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <p>
 * The parser of {@code module} tags in Spring xml configurations.
 * </p>
 * <p>
 * It will instantiate all modules and create bean definition for all its bindings, using
 * {@link com.atlassian.inject.spring.SpringContainer}
 * </p>
 */
public class ModulesBeanDefinitionParser implements BeanDefinitionParser {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BeanDefinition parse(final Element element, final ParserContext parserContext) {
        final NodeList modules = getModuleSubElements(element);
        if (modules.getLength() == 0) {
            logger.debug("Modules element doesn't have any explicit module defined.");
        }

        final Binder binder = getBinder(parserContext);
        for (int i = 0; i < modules.getLength(); i++) {
            parseModule((Element) modules.item(i), binder);
        }

        binder.apply();
        return null;
    }

    /**
     *
     * @param element
     * @param binder
     */
    private void parseModule(final Element element, final Binder binder) {
        final String moduleClass = getModuleClass(element);
        if (moduleClass == null) {
            throw new IllegalStateException("Attribute 'class' must be defined for element module");
        }

        final AbstractModule module = getModule(moduleClass);
        logger.debug("Configuring module <{}>", module.getClass().getName());
        module.configure(binder);
    }

    /**
     *
     * @param parserContext
     * @return
     */
    private Binder getBinder(final ParserContext parserContext) {
        return new DefaultBinder(new SpringContext(parserContext.getRegistry()));
    }

    /**
     *
     * @param element
     * @return
     */
    private NodeList getModuleSubElements(final Element element) {
        return element.getElementsByTagNameNS(element.getNamespaceURI(), "module");
    }

    /**
     *
     * @param moduleClass
     * @return
     */
    private AbstractModule getModule(final String moduleClass) {
        try {
            return (AbstractModule) ClassUtils.forName(moduleClass).newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException("Failed to instantiate class <" + moduleClass
                    + ">, does it have a default constructor? Is it a concrete class?", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to find a public default constructor for  <" //
                    + moduleClass + ">", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to find module class <" + moduleClass + ">", e);
        } catch (ClassCastException e) {
            throw new IllegalStateException("Failed to cast module class <" + moduleClass + "> to <"
                    + AbstractModule.class.getName() + ">", e);
        }
    }

    /**
     *
     * @param element
     * @return
     */
    private String getModuleClass(final Element element) {
        return element.getAttribute("class");
    }
}
