package org.cfr.commons.util.jdbc.access.impl.mysql;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.cfr.commons.util.jdbc.access.Database;
import org.cfr.commons.util.jdbc.access.impl.BaseAdapter;


public class MySqlAdapter extends BaseAdapter {

    @Override
    public Database getDatabase() {
        return Database.mysql;
    }

    @Override
    public Collection<String> checkForeignKeyStatements() {
        return Arrays.asList("SET FOREIGN_KEY_CHECKS=1");
    }

    @Override
    public Collection<String> unCheckForeignKeyStatements() {
        return Arrays.asList("SET FOREIGN_KEY_CHECKS=0");
    }

    @Override
    public Collection<String> shudownStatements() {
        return Collections.emptyList();
    }

    @Override
    public void realeaseConnection() throws SQLException {

    }
}
