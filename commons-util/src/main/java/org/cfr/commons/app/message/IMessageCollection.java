/**
 * Copyright 2014 devacfr<christophefriederich@mac.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cfr.commons.app.message;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * A collection of messages that haven't been resolved
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public interface IMessageCollection {

    /**
     * Adds a message to the collection
     *
     * @param key
     *            The i18n key. Can not be {@code null} or empty.
     * @param arguments
     *            The arguments to insert into the resolved message
     * @throws IllegalArgumentException
     *             if {@code key} is {@code null} or empty.
     */
    void addMessage(String key, Serializable... arguments);

    /**
     * Adds a message to the collection
     *
     * @param message
     *            the message. Can not be {@code null}.
     * @throws IllegalArgumentException
     *             if {@code message} is {@code null}.
     */
    void addMessage(@Nonnull IMessage message);

    /**
     * Adds all messages to the collection
     *
     * @param messages
     *            The list of messages. Can not be {@code null}.
     * @throws IllegalArgumentException
     *             if {@code messages} is {@code null}.
     */
    void addAll(@Nonnull List<IMessage> messages);

    /**
     * @return True if the collection is empty
     */
    boolean isEmpty();

    /**
     * @return the list of messages
     */
    @Nonnull
    List<IMessage> getMessages();

}