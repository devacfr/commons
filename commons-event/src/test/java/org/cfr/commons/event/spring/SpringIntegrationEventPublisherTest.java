package org.cfr.commons.event.spring;

import org.cfr.commons.event.TestEvent;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

import com.atlassian.event.api.EventPublisher;

@ContextConfiguration(locations = { "classpath:org/cfr/commons/event/spring/beans-definitions-tests.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SpringIntegrationEventPublisherTest extends AbstractSpringContextTests {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private PostProcessorListener postProcessorListener;

    @Autowired
    private EventPublisherAwared eventPublisherAwared;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        postProcessorListener.reset();
    }

    @Test
    public void publishEvent() throws InterruptedException {
        TestEvent event = new TestEvent(this);
        eventPublisher.publish(event);
        assertEquals(1, postProcessorListener.getCounter());
    }

    @Test
    public void AwaredListernerPublishEvent() {
        eventPublisherAwared.publishEvent();
        assertEquals(1, postProcessorListener.getCounter());
    }

}
