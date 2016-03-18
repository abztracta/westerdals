package no.woact.pg4100.assignment2.ronesp13.server;

import no.woact.pg4100.assignment2.ronesp13.server.db.MySQLConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class QuizProtocolTest {

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
    public void testHandleInput() throws Exception {
        QuizProtocol protocol = new QuizProtocol();
        String input;
        String output = protocol.handleInput(null);
        assertEquals("Welcome message", "Vil du delta i forfatter-QUIZ? (ja/nei)", output);
        input = "ja";
        output = protocol.handleInput(input);
        assertTrue("A question from the quiz", output.startsWith("Hvem har skrevet "));
        input = "Garantert feil svar";
        output = protocol.handleInput(input);
        assertTrue("Should be wrong answer", output.startsWith("Feil!"));
        input = "ja";
        output = protocol.handleInput(input);
        assertTrue("Answer yes and get a new question", output.startsWith("Hvem har skrevet "));
    }
}