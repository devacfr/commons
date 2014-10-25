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
