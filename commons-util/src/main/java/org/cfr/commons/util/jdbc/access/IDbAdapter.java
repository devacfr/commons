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
package org.cfr.commons.util.jdbc.access;

import java.sql.SQLException;
import java.util.Collection;

import javax.annotation.Nonnull;

/**
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public interface IDbAdapter {

    /**
     * Get the type of database of this adapter.
     *
     * @return Returns the {@link Database type} of database.
     */
    @Nonnull
    Database getDatabase();

    /**
     * @return Returns list containing statement to execute to remove check constraints.
     */
    @Nonnull
    Collection<String> unCheckForeignKeyStatements();

    /**
     * @return Returns list containing statement to execute to add check constraints.
     */
    @Nonnull
    Collection<String> checkForeignKeyStatements();

    /**
     * @return Returns list containing statement to execute to shutdown database.
     */
    @Nonnull
    Collection<String> shudownStatements();

    /**
     * Performs a checkpoint and releases resources for embedded databases typically.
     *
     * @throws SQLException
     *             if a database access error occurs
     */
    @Nonnull
    void shudownDatabase() throws SQLException;
}
