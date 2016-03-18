package no.woact.pg4100.assignment2.ronesp13.server.model.handler;

import no.woact.pg4100.assignment2.ronesp13.server.db.MySQLConnector;
import no.woact.pg4100.assignment2.ronesp13.server.model.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class BookHandlerTest {

    private MySQLConnector connector;

    @Before
    public void setUp() throws Exception {
        connector = MySQLConnector.getInstance();
        connector.createConnection("localhost", "pg4100_assignment2_test", "root", "root");
    }

    @After
    public void tearDown() throws Exception {
        connector.getConnection().close();
    }

    @Test
    public void testGetBook() throws Exception {
        BookHandler handler = new BookHandler();
        Book book = handler.getBook();
        assertNotNull("Book should not be null", book);
    }
}