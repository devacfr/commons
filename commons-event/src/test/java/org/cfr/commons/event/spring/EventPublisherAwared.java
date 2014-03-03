package org.cfr.commons.event.spring;

import org.cfr.commons.event.TestEvent;
import org.cfr.commons.event.api.IEventPublisherAware;
import org.cfr.commons.util.Assert;

import com.atlassian.event.api.EventPublisher;

public class EventPublisherAwared implements IEventPublisherAware {

    private EventPublisher eventPublisher;

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = Assert.notNull(eventPublisher);

    }

    public void publishEvent() {
        this.eventPublisher.publish(new TestEvent(this));
    }
}
