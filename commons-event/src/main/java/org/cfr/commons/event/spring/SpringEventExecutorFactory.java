package org.cfr.commons.event.spring;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import com.atlassian.event.spi.EventExecutorFactory;

/**
 * 
 * @author devacfr
 * @since 1.0
 *
 */
public class SpringEventExecutorFactory implements EventExecutorFactory {

    /**
     * 
     */
    private final ExecutorService executorService;

    /**
     * 
     * @param executorService
     */
    public SpringEventExecutorFactory(final ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Executor getExecutor() {
        return this.executorService;
    }
}
