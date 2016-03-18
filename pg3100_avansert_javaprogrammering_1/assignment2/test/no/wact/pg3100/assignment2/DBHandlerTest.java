package no.wact.pg3100.assignment2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DBHandlerTest {

    // Database credentials
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    // SQL Queries
    private static final String SELECT_TABLE_STATEMENT = "SELECT * FROM `test_table`";
    private static final String DELETE_TABLE_STATEMENT = "DROP TABLE IF EXISTS `test_table`;";
    private static final String TEST_STATEMENT = "SELECT `name`, `subjects`, `email`, `position` FROM `test_table`";

    private DBHandler dbHandler;

    @Before
    public void setUp() throws Exception {
        dbHandler = new DBHandler(DB_USERNAME, DB_PASSWORD);
    }

    @After
    public void tearDown() throws Exception {
        dropTestTable();
        dbHandler.close();
    }

    @Test
    public void testGetConnection() throws Exception {
        Connection connection = dbHandler.getConnection();
        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    public void testClose() throws Exception {
        DBHandler db = new DBHandler(DB_USERNAME, DB_PASSWORD);
        db.close();
        assertTrue(db.getConnection().isClosed());
    }

    @Test
    public void testCopyFileWithValidFile() throws Exception {
        dbHandler.copyFile("tekstfil_test_valid.txt", "test_table");
        try (
                Statement statement = dbHandler.getConnection().createStatement();
                ResultSet rs = statement.executeQuery(TEST_STATEMENT)
        ) {
            rs.next();
            assertEquals("Eivind Brevik", rs.getString("name"));
            assertEquals(3, rs.getInt("subjects"));
            assertEquals("breeiv@nith.no", rs.getString("email"));
            assertEquals("Høgskolelektor", rs.getString("position"));
            rs.next();
            assertEquals("Bjørn Olav Listog", rs.getString("name"));
            assertEquals(2, rs.getInt("subjects"));
            assertEquals("blistog@nith.no", rs.getString("email"));
            assertEquals("Høgskolelektor", rs.getString("position"));
            rs.next();
            assertEquals("Per Lauvås", rs.getString("name"));
            assertEquals(5, rs.getInt("subjects"));
            assertEquals("lauper@nith.no", rs.getString("email"));
            assertEquals("Høgskolelektor", rs.getString("position"));
            assertFalse("There are more rows in the result set", rs.next());
        }
        dropTestTable();
    }

    @Test
    public void testCopyFileWithFileThatDoesNotExist() throws Exception {
        dbHandler.copyFile("afilewhichdoesnotexist.gg", "test_table");
        assertThatTestTableIsNotCreated();
    }

    @Test
    public void testCopyFileWithNullOrNameWithLengthZero() throws Exception {
        dbHandler.copyFile("", "test_table");
        assertThatTestTableIsNotCreated();
        dbHandler.copyFile(null, "test_table");
        assertThatTestTableIsNotCreated();
    }

    @Test
    public void testCopyFileWithInvalidDatatypes() throws Exception {
        dbHandler.copyFile("tekstfil_test_invalid_datatype.txt", "test_table");
        assertThatTestTableIsNotCreated();
        dbHandler.copyFile("tekstfil_test_invalid_data_invalid_amount_of_attributes.txt", "test_table");
        assertThatTestTableIsNotCreated();
    }

    private void assertThatTestTableIsNotCreated() throws Exception {
        try (
                Statement statement = dbHandler.getConnection().createStatement()
        ) {
            statement.executeQuery(SELECT_TABLE_STATEMENT);
        } catch (SQLException e) {
            assertEquals("Table 'pg3100_assignment2.test_table' doesn't exist", e.getMessage());
        }
    }

    private void dropTestTable() throws Exception {
        try (Statement statement = dbHandler.getConnection().createStatement()) {
            statement.execute(DELETE_TABLE_STATEMENT);
        }
    }

    @Test
    public void testGetTable() throws Exception {
        assertNull(dbHandler.getTable(null));
        assertNull(dbHandler.getTable(""));
        assertNull(dbHandler.getTable("test_table"));
        dbHandler.copyFile("tekstfil_test_valid.txt", "test_table");
        assertNull(dbHandler.getTable(null));
        assertNull(dbHandler.getTable(""));
        DBTable table = dbHandler.getTable("test_table");
        assertNotNull(table);
        assertEquals("test_table", table.getTablename());

        // assert that rows and columns contains correct data
        List<DBColumn> columnList = table.getColumns();
        List<DBRow> rowList = table.getRows();
        assertNotNull(columnList);
        assertNotNull(rowList);

        // assert metadata
        assertEquals(5, columnList.size());
        assertEquals("uid", columnList.get(0).getName());
        assertEquals("INT", columnList.get(0).getDatatype());
        assertEquals(11, columnList.get(0).getLength());
        assertEquals("name", columnList.get(1).getName());
        assertEquals("STRING", columnList.get(1).getDatatype());
        assertEquals(128, columnList.get(1).getLength());
        assertEquals("subjects", columnList.get(2).getName());
        assertEquals("INT", columnList.get(2).getDatatype());
        assertEquals(3, columnList.get(2).getLength());
        assertEquals("email", columnList.get(3).getName());
        assertEquals("STRING", columnList.get(3).getDatatype());
        assertEquals(128, columnList.get(3).getLength());
        assertEquals("position", columnList.get(4).getName());
        assertEquals("STRING", columnList.get(4).getDatatype());
        assertEquals(128, columnList.get(4).getLength());

        // assert data
        assertEquals(3, rowList.size());
        assertEquals(5, rowList.get(0).getAttributes().size());
        assertEquals("1", rowList.get(0).getAttribute("uid"));
        assertEquals("Eivind Brevik", rowList.get(0).getAttribute("name"));
        assertEquals("3", rowList.get(0).getAttribute("subjects"));
        assertEquals("breeiv@nith.no", rowList.get(0).getAttribute("email"));
        assertEquals("Høgskolelektor", rowList.get(0).getAttribute("position"));
        assertEquals(5, rowList.get(1).getAttributes().size());
        assertEquals("2", rowList.get(1).getAttribute("uid"));
        assertEquals("Bjørn Olav Listog", rowList.get(1).getAttribute("name"));
        assertEquals("2", rowList.get(1).getAttribute("subjects"));
        assertEquals("blistog@nith.no", rowList.get(1).getAttribute("email"));
        assertEquals("Høgskolelektor", rowList.get(1).getAttribute("position"));
        assertEquals(5, rowList.get(2).getAttributes().size());
        assertEquals("3", rowList.get(2).getAttribute("uid"));
        assertEquals("Per Lauvås", rowList.get(2).getAttribute("name"));
        assertEquals("5", rowList.get(2).getAttribute("subjects"));
        assertEquals("lauper@nith.no", rowList.get(2).getAttribute("email"));
        assertEquals("Høgskolelektor", rowList.get(2).getAttribute("position"));
    }

    @Test
    public void testCopyFileWithClosedConnection() throws Exception {
        DBHandler db = new DBHandler("root", "root");
        db.close();
        db.copyFile("tekstfil_test_valid.txt", "test_table");
        assertThatTestTableIsNotCreated();
    }
}