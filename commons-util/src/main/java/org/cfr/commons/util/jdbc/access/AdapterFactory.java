package org.cfr.commons.util.jdbc.access;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.cfr.commons.util.jdbc.access.impl.BaseAdapterManager;
import org.cfr.commons.util.jdbc.access.impl.derby.DerbyAdapterManager;
import org.cfr.commons.util.jdbc.access.impl.hsqldb.HsqlDbAdapterManager;
import org.cfr.commons.util.jdbc.access.impl.mysql.MySqlAdapterManager;

import com.google.common.collect.Lists;

public class AdapterFactory {

    private static ArrayList<BaseAdapterManager> adaptersMgr = null;

    private static IDbAdapter adapterCurrent;

    static {
        adaptersMgr = Lists.newArrayList(new MySqlAdapterManager(), new DerbyAdapterManager(), new HsqlDbAdapterManager());
    }

    public static IDbAdapter getCurrentAdapter(Connection connection) throws SQLException {
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

    public static IDbAdapter getAdapter(Connection connection) throws SQLException {
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
