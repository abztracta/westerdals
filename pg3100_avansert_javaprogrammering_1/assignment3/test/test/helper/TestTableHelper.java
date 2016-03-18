package test.helper;

import no.wact.pg3100.assignment3.config.Config;
import no.wact.pg3100.assignment3.db.MySQLConnector;

import java.sql.SQLException;
import java.sql.Statement;

public class TestTableHelper {

    public static void createTableAndFixtures() throws Exception {
        try (
                MySQLConnector connector = new MySQLConnector(Config.DB_USERNAME, Config.DB_PASSWORD);
                Statement statement = connector.getConnection().createStatement()
        ) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `albums_test` (" +
                    "  `artist` VARCHAR(25) NOT NULL," +
                    "  `title` VARCHAR(240) NOT NULL," +
                    "  `tracks` SMALLINT(2) NOT NULL," +
                    "  `released` SMALLINT(4) NOT NULL," +
                    "  `genre` VARCHAR(15) NOT NULL," +
                    "  PRIMARY KEY (`artist`, `title`)" +
                    ");");
            createFixtures(connector);
        }
    }

    private static void createFixtures(MySQLConnector connector) throws SQLException {
        try (Statement statement = connector.getConnection().createStatement()) {
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Deep Purple', 'In Rock', 8, 1970, 'Rock');");
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Bruce Springsteen', 'Darkness on the Edge of Town', 5, 1978, 'Rock');");
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Terje Rypdal', 'Terje Rypdal', 5, 1971, 'Jazz');\n");
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Dewey Redman', 'The Struggle Continues', 4, 1982, 'Jazz');");
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Ole Paus', 'Garman', 10, 1972, 'Viser');");
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Odd Nordstoga', 'Luring', 9, 2004, 'Viser');");
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Bob Marley', 'Exodus', 7, 1977, 'Reggae');");
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Peter Tosh', 'Legalize It', 11, 1976, 'Reggae');");
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Bobby Bare', 'This I Believe', 9, 1971, 'Country');");
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Emmylou Harris', 'Blue Kentucky Girl', 11, 1979, 'Country');");
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Miles Davis', 'Bitches Brew', 10, 1970, 'Jazz');");
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Chick Corea', 'Children\\'s Song', 15, 1983, 'Jazz');");
            statement.addBatch("INSERT INTO `albums_test` VALUES ('Chris Spedding', 'Hurt', 8, 1977, 'Rock');");
            statement.executeBatch();
        }
    }

    public static void removeTestTable() throws Exception {
        try (
                MySQLConnector connector = new MySQLConnector(Config.DB_USERNAME, Config.DB_PASSWORD);
                Statement statement = connector.getConnection().createStatement()
        ) {
            statement.execute("DROP TABLE " + Config.DB_TABLE);
        }
    }
}
