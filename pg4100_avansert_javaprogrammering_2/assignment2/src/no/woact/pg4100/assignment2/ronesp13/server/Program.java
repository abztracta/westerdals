package no.woact.pg4100.assignment2.ronesp13.server;

import no.woact.pg4100.assignment2.ronesp13.server.db.MySQLConnector;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class Program {

    private static final Logger log = Logger.getLogger(Program.class.getName());

    public static void main(String[] args) {
        try {
            MySQLConnector.getInstance().createConnection("localhost", "pg4100_assignment2", "root", "root");
        } catch (SQLException e) {
            log.severe("Could not establish database-connection. " + e.getMessage());
        }
        new LoginServer();
    }
}
