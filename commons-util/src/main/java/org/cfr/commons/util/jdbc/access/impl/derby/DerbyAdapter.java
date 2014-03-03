package org.cfr.commons.util.jdbc.access.impl.derby;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

import org.cfr.commons.util.jdbc.access.Database;
import org.cfr.commons.util.jdbc.access.impl.BaseAdapter;


public class DerbyAdapter extends BaseAdapter {

    @Override
    public Database getDatabase() {
        return Database.derby;
    }

    @Override
    public Collection<String> checkForeignKeyStatements() {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> unCheckForeignKeyStatements() {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> shudownStatements() {
        return Collections.emptyList();
    }

    @Override
    public void realeaseConnection() throws SQLException {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            // noop
        }

    }

}