package org.cfr.commons.util.jdbc.access.impl.hsqldb;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.cfr.commons.util.jdbc.access.Database;
import org.cfr.commons.util.jdbc.access.impl.BaseAdapter;


public class HsqlDbAdapter extends BaseAdapter {

    @Override
    public Database getDatabase() {
        return Database.hsqldb;
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
        return Arrays.asList("SHUTDOWN COMPACT");
    }

    @Override
    public void realeaseConnection() throws SQLException {
    }
}