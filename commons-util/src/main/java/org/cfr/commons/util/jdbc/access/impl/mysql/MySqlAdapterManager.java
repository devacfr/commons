package org.cfr.commons.util.jdbc.access.impl.mysql;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.cfr.commons.util.jdbc.access.IDbAdapter;
import org.cfr.commons.util.jdbc.access.impl.BaseAdapterManager;


public class MySqlAdapterManager extends BaseAdapterManager {

	@Override
	public IDbAdapter createAdapter(DatabaseMetaData md) throws SQLException {
        String dbName = md.getDatabaseProductName();
        if (dbName == null || !dbName.toUpperCase().contains("MYSQL")) {
            return null;
        }

        MySqlAdapter adapter = new MySqlAdapter();
        return adapter;
    }


}
