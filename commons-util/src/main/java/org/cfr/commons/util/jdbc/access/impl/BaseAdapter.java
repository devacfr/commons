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
package org.cfr.commons.util.jdbc.access.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

import org.cfr.commons.util.jdbc.access.IDbAdapter;

/**
 * Abstract class of interface {@link IDbAdapter}
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public abstract class BaseAdapter implements IDbAdapter {

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> checkForeignKeyStatements() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> unCheckForeignKeyStatements() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    public void shudownDatabase() throws SQLException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> shudownStatements() {
        return Collections.emptyList();
    }

}
