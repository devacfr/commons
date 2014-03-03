package org.cfr.commons.event.spring;

import org.cfr.commons.event.TestEvent;

import com.atlassian.event.api.EventListener;

public class PostProcessorListener {

    private int counter = 0;

    @EventListener
    public void onEvent(TestEvent event) {
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public void reset() {
        counter = 0;

    }
}
