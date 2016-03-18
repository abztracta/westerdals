package no.wact.pg3100.assignment2;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class MySQLConnector implements AutoCloseable {

    private static final String DEFAULT_SCHEMA = "pg3100_assignment2";
    private Connection connection;

    public MySQLConnector(String hostname, String schema, String username, String password) throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(hostname);
        dataSource.setDatabaseName(schema);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        connection = dataSource.getConnection();
    }

    public MySQLConnector(String username, String password) throws SQLException {
        this("localhost", DEFAULT_SCHEMA, username, password);
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
