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

import org.cfr.commons.util.jdbc.access.impl.BaseAdapterManager;
import org.cfr.commons.util.jdbc.access.impl.derby.DerbyAdapterManager;
import org.cfr.commons.util.jdbc.access.impl.hsqldb.HsqlDbAdapterManager;
import org.cfr.commons.util.jdbc.access.impl.mysql.MySqlAdapterManager;

import com.google.common.collect.Lists;

/**
 * 
 * @author devacfr
 *
 */
public final class AdapterFactory {

    /**
     * 
     */
    private static ArrayList<BaseAdapterManager> adaptersMgr = null;

    /**
     * 
     */
    private static IDbAdapter adapterCurrent;

    static {
        adaptersMgr = Lists.newArrayList(new MySqlAdapterManager(), new DerbyAdapterManager(),
                new HsqlDbAdapterManager());
    }

    private AdapterFactory() {
    }

    /**
     * 
     * @param connection
     * @return
     * @throws SQLException
     */
    public static IDbAdapter getCurrentAdapter(final Connection connection) throws SQLException {
        synchronized (AdapterFactory.class) {
            if (adapterCurrent == null) {
                for (BaseAdapterManager mgr : adaptersMgr) {
                    IDbAdapter adapter = mgr.createAdapter(connection.getMetaData());
                    if (adapter != null) {
                        adapterCurrent = adapter;
                        break;
                    }
                }
            }
        }
        return adapterCurrent;
    }

    public static IDbAdapter getAdapter(final Connection connection) throws SQLException {
        synchronized (AdapterFactory.class) {
            for (BaseAdapterManager mgr : adaptersMgr) {
                IDbAdapter adapter = mgr.createAdapter(connection.getMetaData());
                if (adapter != null) {
                    return adapter;
                }
            }
        }
        return null;
    }

}
