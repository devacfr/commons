package org.cfr.commons.app.process;

/**
 * Used to shut something down.
 *
 * @since v4.0
 */
public interface IShutdown {

    /**
     * Shutdown. Should not throw any exceptions.
     */
    void shutdown();
}