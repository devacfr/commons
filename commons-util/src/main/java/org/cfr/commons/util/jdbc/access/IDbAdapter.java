package org.cfr.commons.util.jdbc.access;

import java.sql.SQLException;
import java.util.Collection;

public interface IDbAdapter {

    Database getDatabase();

    Collection<String> unCheckForeignKeyStatements();

    Collection<String> checkForeignKeyStatements();

    Collection<String> shudownStatements();

    void realeaseConnection() throws SQLException;
}
