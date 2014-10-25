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
import java.util.Locale;
import java.util.Map;

/**
 * This interface is responsible for resolving a message or key/argument pairs to their internationalized message.
 *
 * @since 1.0
 */
public interface I18nResolver {

    /**
     * Given a key and a list of arguments this method returns the i18ned text if it can be resolved. Arguments may also
     * be of the form {@link IMessage} which means they will be resolved as well before being included as an argument.
     *
     * @param key
     *            key for the i18ned message
     * @param arguments
     *            Optional list of arguments for the message.
     * @return I18ned string
     */
    String getText(String key, Serializable... arguments);

    /**
     * Does the same as {@link #getText(String, java.io.Serializable...)} however it is needed for velocity.
     * 
     * @param key
     *            key for the i18ned message
     * @return I18ned string
     */
    String getText(String key);

    /**
     * Given a {@link IMessage} this method returns the i18ned text if it can be resolved.
     * 
     * @param message
     *            The message to i18n
     * @return I18ned string
     */
    String getText(IMessage message);

    /**
     * Creates an instance of Message.
     * 
     * @param key
     *            The message key
     * @param arguments
     *            The arguments to interpolate
     * @return The message
     */
    IMessage createMessage(String key, Serializable... arguments);

    /**
     * @return an instance of MessageCollection.
     */
    IMessageCollection createMessageCollection();

    /**
     * Given a prefix, this method will return all translations where the key starts with the given prefix as key ->
     * value mappings, using the default locale.
     *
     * @param prefix
     *            The prefix for a particular key to start with. Empty string will match everything, which may be slow.
     *            Throws {@code NullPointerException} if {@code null}.
     * @return A Map of i18nKey -> translation mappings where i18nKey starts with the prefix. Empty map if no matches.
     * @throws NullPointerException
     *             if {@code link} is {@code null}
     * @since 2.1
     */
    Map<String, String> getAllTranslationsForPrefix(String prefix);

    /**
     * Given a prefix, this method will return all translations where the key starts with the given prefix as key ->
     * value mappings.
     *
     * @param prefix
     *            The prefix for a particular key to start with. Empty string will match everything, which may be slow.
     *            Throws {@code NullPointerException} if {@code null}.
     * @param locale
     *            The locale for which to lookup translations. Throws {@code NullPointerException} if {@code null}.
     * @return A Map of i18nKey -> translation mappings where i18nKey starts with the prefix. Empty map if no matches.
     * @throws NullPointerException
     *             if {@code prefix} or {@code link} are {@code null}
     */
    Map<String, String> getAllTranslationsForPrefix(String prefix, Locale locale);
}