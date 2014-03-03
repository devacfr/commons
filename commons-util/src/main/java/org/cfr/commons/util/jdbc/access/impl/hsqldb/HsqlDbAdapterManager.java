package org.cfr.commons.util.jdbc.access.impl.hsqldb;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.cfr.commons.util.jdbc.access.IDbAdapter;
import org.cfr.commons.util.jdbc.access.impl.BaseAdapterManager;


public class HsqlDbAdapterManager extends BaseAdapterManager {

    @Override
    public IDbAdapter createAdapter(DatabaseMetaData md) throws SQLException {
        String dbName = md.getDatabaseProductName();
        if (dbName == null || !dbName.toUpperCase().contains("HSQL")) {
            return null;
        }

        HsqlDbAdapter adapter = new HsqlDbAdapter();
        return adapter;
    }

}
