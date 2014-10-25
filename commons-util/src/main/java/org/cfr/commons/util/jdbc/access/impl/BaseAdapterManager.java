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

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.cfr.commons.util.jdbc.access.IDbAdapter;

/**
 * 
 * @author devacfr
 *
 */
public abstract class BaseAdapterManager {

    /**
     * 
     * @param md
     * @return
     * @throws SQLException
     */
    public abstract IDbAdapter createAdapter(DatabaseMetaData md) throws SQLException;
}
