package org.cfr.commons.app.process;

/**
 * Implementing this interface allows Components to be notified of when the application has started.
 *
 * <p> After the plugin system is initialised and components added to the dependency injection framework, then components
 * implementing this interface will have their {@link #start()} method called.
 * Note that only plugin modules of type Component will be considered as "Startable".
 */
public interface IStartable {

    /**
     * This method wil be called after the plugin system is fully initialised and all components added to the
     * dependency injection framework.
     *
     * @throws Exception Allows implementations to throw an Exception.
     */
    public void start() throws Exception;
}
