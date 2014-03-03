package org.cfr.commons.event.api;

import com.atlassian.event.spi.ListenerHandler;

/**
 * Interface to be implemented by {@link ListenerHandler} that wish to be
 * supported by this spring Atlassian event publisher implementation.
 * @author devacfr
 * @since 1.0
 */
public interface ISupportedListenerHandler extends ListenerHandler {

    /**
     * Gets the indicating whethter the <code>listener</code> contains
     * at least a method supported by this {@link ListenerHandler}.
     * @param listener listener to check the support of this {@link ListenerHandler}.
     * @return Returns <code>true</code> whether the <code>listener</code> contains
     * at least a method supported by this {@link ListenerHandler},
     * otherwise returns <code>false</code>. 
     */
    boolean supportsHandler(Object listener);
}
