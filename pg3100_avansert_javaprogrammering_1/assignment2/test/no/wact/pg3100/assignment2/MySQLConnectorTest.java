package no.wact.pg3100.assignment2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MySQLConnectorTest {

    // Database credentials
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private MySQLConnector connector;

    @Before
    public void setUp() throws Exception {
        connector = new MySQLConnector(USERNAME, PASSWORD);
    }

    @After
    public void tearDown() throws Exception {
        connector.close();
    }

    @Test
    public void testClose() throws Exception {
        assertFalse(connector.getConnection().isClosed());
        connector.getConnection().close();
        assertTrue(connector.getConnection().isClosed());
    }

    @Test
    public void testGetConnection() throws Exception {
        assertNotNull(connector);
        assertNotNull(connector.getConnection());
        assertFalse(connector.getConnection().isClosed());
    }
}