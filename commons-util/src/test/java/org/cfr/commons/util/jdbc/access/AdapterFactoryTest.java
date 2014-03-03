package org.cfr.commons.util.jdbc.access;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.cfr.commons.testing.EasyMockTestCase;
import org.cfr.commons.util.jdbc.access.AdapterFactory;
import org.cfr.commons.util.jdbc.access.IDbAdapter;
import org.junit.Test;


public class AdapterFactoryTest extends EasyMockTestCase {

    @Test
    public void getKnownCurrentAdapter() throws SQLException {
        // Mysql Adapter
        Connection connection = mock(Connection.class);
        DatabaseMetaData metaData = mock(DatabaseMetaData.class);
        expect(connection.getMetaData()).andReturn(metaData).once();
        expect(metaData.getDatabaseProductName()).andReturn("DATABASE mySql").once();

        replay();
        IDbAdapter adapter = AdapterFactory.getCurrentAdapter(connection);
        assertNotNull(adapter);
        verify();
    }

    @Test
    public void getUnknownAdapter() throws SQLException {
        // Mysql Adapter
        Connection connection = mock(Connection.class);
        DatabaseMetaData metaData = mock(DatabaseMetaData.class);
        expect(connection.getMetaData()).andReturn(metaData).anyTimes();
        expect(metaData.getDatabaseProductName()).andReturn("DATABASE unknown").once();

        replay();
        IDbAdapter adapter = AdapterFactory.getAdapter(connection);
        assertNull(adapter);
        verify();
    }

}
