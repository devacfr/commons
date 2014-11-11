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
package org.cfr.commons.util.jdbc.access.impl.mysql;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.cfr.commons.util.Assert;
import org.cfr.commons.util.jdbc.access.IDbAdapter;
import org.cfr.commons.util.jdbc.access.impl.AbstractAdapterManager;

/**
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class MySqlAdapterManager extends AbstractAdapterManager {

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable IDbAdapter createAdapter(@Nonnull final DatabaseMetaData metadata) throws SQLException {
        String dbName = Assert.checkNotNull(metadata, "metadata").getDatabaseProductName();
        if (dbName == null || !dbName.toUpperCase().contains("MYSQL")) {
            return null;
        }
        return new MySqlAdapter();
    }

}
