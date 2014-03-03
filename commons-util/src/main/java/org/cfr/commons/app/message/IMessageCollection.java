package org.cfr.commons.app.message;

import java.io.Serializable;
import java.util.List;

/**
 * A collection of messages that haven't been resolved
 *
 * @since 2.0
 */
public interface IMessageCollection {

    /**
     * Adds a message to the collection
     * @param key The i18n key
     * @param arguments The arguments to insert into the resolved message
     */
    void addMessage(String key, Serializable... arguments);

    /**
     * Adds a message to the collection
     * @param message the message
     */
    void addMessage(IMessage message);

    /**
     * Adds all messages to the collection
     *
     * @param messages The list of messages
     */
    void addAll(List<IMessage> messages);

    /**
     * @return True if the collection is empty
     */
    boolean isEmpty();

    /**
     * @return the list of messages
     */
    List<IMessage> getMessages();

}