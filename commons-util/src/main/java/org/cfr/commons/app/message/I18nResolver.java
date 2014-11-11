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

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This interface is responsible for resolving a message or key/argument pairs to their internationalized message.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public interface I18nResolver {

    /**
     * Given a key and a list of arguments this method returns the i18ned text if it can be resolved. Arguments may also
     * be of the form {@link IMessage} which means they will be resolved as well before being included as an argument.
     *
     * @param key
     *            key for the i18ned message. Can not be {@code null} or empty.
     * @param arguments
     *            Optional list of arguments for the message.
     * @return I18ned string
     * @throws IllegalArgumentException
     *             Throws {@code key} if {@code null}.
     */
    @CheckReturnValue
    @Nullable
    String getText(@Nonnull String key, @Nullable Serializable... arguments);

    /**
     * Does the same as {@link #getText(String, java.io.Serializable...)} however it is needed for velocity.
     *
     * @param key
     *            key for the i18ned message. Can not be {@code null} or empty.
     * @return I18ned string
     * @throws IllegalArgumentException
     *             Throws {@code key} if {@code null} or empty.
     */
    @CheckReturnValue
    @Nullable
    String getText(@Nonnull String key);

    /**
     * Given a {@link IMessage} this method returns the i18ned text if it can be resolved.
     *
     * @param message
     *            The message to i18n. Can not be {@code null}.
     * @return I18ned string
     * @throws IllegalArgumentException
     *             Throws {@code message} if {@code null} or empty.
     */
    @Nullable
    String getText(@Nonnull IMessage message);

    /**
     * Creates an instance of Message.
     *
     * @param key
     *            The message key. Can not be {@code null} or empty.
     * @param arguments
     *            The arguments to interpolate
     * @return The message.
     * @throws IllegalArgumentException
     *             Throws {@code key} if {@code null}.
     */
    @Nonnull
    IMessage createMessage(@Nonnull String key, @Nullable Serializable... arguments);

    /**
     * @return an instance of MessageCollection.
     */
    @Nonnull
    IMessageCollection createMessageCollection();

    /**
     * Given a prefix, this method will return all translations where the key starts with the given prefix as key ->
     * value mappings, using the default locale.
     *
     * @param prefix
     *            The prefix for a particular key to start with. Empty string will match everything, which may be slow.
     *            Can not be {@code null} or empty.
     * @return A Map of i18nKey -> translation mappings where i18nKey starts with the prefix. Empty map if no matches.
     * @throws IllegalArgumentException
     *             if {@code prefix} is {@code null} or empty.
     */
    @Nonnull
    Map<String, String> getAllTranslationsForPrefix(@Nonnull String prefix);

    /**
     * Given a prefix, this method will return all translations where the key starts with the given prefix as key ->
     * value mappings.
     *
     * @param prefix
     *            The prefix for a particular key to start with. Empty string will match everything, which may be slow.
     *            Can not be {@code null} or empty.
     * @param locale
     *            The locale for which to lookup translations. Can not be {@code null}.
     * @return A Map of i18nKey -> translation mappings where i18nKey starts with the prefix. Empty map if no matches.
     * @throws IllegalArgumentException
     *             if {@code prefix} is {@code null} or empty and {@code local} is {@code null}.
     */
    @Nonnull
    Map<String, String> getAllTranslationsForPrefix(@Nonnull String prefix, @Nonnull Locale locale);
}