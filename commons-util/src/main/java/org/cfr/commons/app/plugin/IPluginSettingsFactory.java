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
package org.cfr.commons.app.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Factory for mutable, non-threadsafe PluginSettings objects.
 *
 * @since 1.0
 */
public interface IPluginSettingsFactory {

    /**
     * Gets all settings for a key, for which valid values are application-specific. To store settings for other keys,
     * createGlobalSettings should be used, and the keys should be sensibly namespaced by the plugin.
     *
     * @param key
     *            the key, can be null to retrieve global settings
     * @throws IllegalArgumentException
     *             if no "concept" for the key can be found
     * @return The settings
     */
    @Nonnull
    IPluginSettings createSettingsForKey(@Nullable String key);

    /**
     * Gets all global settings. This is useful to store settings against arbitrary keys. When storing settings against
     * arbitrary keys, plugins are advised to namespace the key with something unique to the plugin (for example
     * "com.example.plugin:key-I-would-like-to-use" ) to avoid clashes with other keys.
     *
     * @return Global settings
     */
    @Nonnull
    IPluginSettings createGlobalSettings();
}
