package org.cfr.commons.util.jdbc.access.impl;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.cfr.commons.util.jdbc.access.IDbAdapter;



public abstract class BaseAdapterManager {


	public abstract IDbAdapter createAdapter(DatabaseMetaData md) throws SQLException;
}
