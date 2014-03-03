package org.cfr.commons.util.jdbc.access.impl.derby;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.cfr.commons.util.jdbc.access.IDbAdapter;
import org.cfr.commons.util.jdbc.access.impl.BaseAdapterManager;


public class DerbyAdapterManager extends BaseAdapterManager {

    @Override
    public IDbAdapter createAdapter(DatabaseMetaData md) throws SQLException {
        String dbName = md.getDatabaseProductName();
        if (dbName == null || !dbName.toUpperCase().contains("DERBY")) {
            return null;
        }

        DerbyAdapter adapter = new DerbyAdapter();
        return adapter;
    }

}
