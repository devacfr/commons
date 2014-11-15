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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.cfr.commons.util.jdbc.access.impl.AbstractAdapterManager;
import org.cfr.commons.util.jdbc.access.impl.derby.DerbyAdapterManager;
import org.cfr.commons.util.jdbc.access.impl.hsqldb.HsqlDbAdapterManager;
import org.cfr.commons.util.jdbc.access.impl.mysql.MySqlAdapterManager;

import com.google.common.collect.Lists;

/**
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public final class AdapterFactory {

    /**
     * List of supported database adapter.
     */
    private static final ArrayList<AbstractAdapterManager> ADAPTER_MANAGERS = Lists.newArrayList(new MySqlAdapterManager(),
        new DerbyAdapterManager(),
        new HsqlDbAdapterManager());

    /**
     * Singleton restriction instantiation of the class
     */
    private AdapterFactory() {
    }

    /**
     * Gets the adapter corresponding to the {@code connection} parameter.
     * 
     * @param connection
     *            the connection
     * @return Returns new adapter instance if database is recognized, otherwise {@code null}.
     * @throws SQLException
     *             if a database access error occurs
     */
    public static @Nullable IDbAdapter getAdapter(@Nonnull final Connection connection) throws SQLException {
        synchronized (AdapterFactory.class) {
            for (AbstractAdapterManager mgr : ADAPTER_MANAGERS) {
                IDbAdapter adapter = mgr.createAdapter(connection.getMetaData());
                if (adapter != null) {
                    return adapter;
                }
            }
        }
        return null;
    }

}
