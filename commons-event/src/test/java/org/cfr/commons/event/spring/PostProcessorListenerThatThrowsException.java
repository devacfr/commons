package org.cfr.commons.event.spring;

import org.cfr.commons.event.TestEvent;

import com.atlassian.event.api.EventListener;

public class PostProcessorListenerThatThrowsException {

    @EventListener
    public void onError(TestEvent event) {
        throw new RuntimeException("error message");
    }

}
