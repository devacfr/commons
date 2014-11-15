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

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Encapsulates a message before it has been resolved via an I18N resolver
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public interface IMessage extends Serializable {

    /**
     * @return the i18n message key
     */
    @Nonnull
    String getKey();

    /**
     * @return the arguments to insert into the resolved message
     */
    @CheckReturnValue
    @Nullable
    Serializable[] getArguments();
}
