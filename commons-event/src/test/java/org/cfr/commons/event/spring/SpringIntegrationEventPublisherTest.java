package org.cfr.commons.event.spring;

import org.cfr.commons.event.TestEvent;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

import com.atlassian.event.api.EventPublisher;

@Configuration
@ContextConfiguration(classes = { SpringIntegrationEventPublisherTest.class })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SpringIntegrationEventPublisherTest extends AbstractSpringContextTests {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private PostProcessorListener postProcessorListener;

    @Autowired
    private EventPublisherAwared eventPublisherAwared;

    @Bean
    public EventPublisherFactoryBean eventPublisher() {
        return new EventPublisherFactoryBean();
    }

    @Bean
    public PostProcessorListener postProcessorListener() {
        return new PostProcessorListener();
    }

    @Bean
    public PostProcessorListenerThatThrowsException postProcessorListenerThatThrowsException() {
        return new PostProcessorListenerThatThrowsException();
    }

    @Bean
    public EventPublisherAwared eventPublisherAwared() {
        return new EventPublisherAwared();
    }

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
