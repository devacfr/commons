package org.cfr.commons.event.spring;

import org.cfr.commons.testing.EasyMockTestCase;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;


@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class })
public abstract class AbstractSpringContextTests extends EasyMockTestCase implements ApplicationContextAware {

    /**
     * Logger available to subclasses.
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The {@link ApplicationContext} that was injected into this test instance
     * via {@link #setApplicationContext(ApplicationContext)}.
     */
    protected ApplicationContext applicationContext;

    /**
     * Set the {@link ApplicationContext} to be used by this test instance,
     * provided via {@link ApplicationContextAware} semantics.
     */
    @Override
    public final void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}