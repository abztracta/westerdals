package no.woact.pg4100.assignment2.ronesp13.server.db;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class MySQLConnector {

    private Connection connection;

    public void createConnection(String hostname, String schema, String username, String password) throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(hostname);
        dataSource.setDatabaseName(schema);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        connection = dataSource.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public static MySQLConnector getInstance() {
        return SingletonHolder.instance;
    }

    private static final class SingletonHolder {
        private static final MySQLConnector instance = new MySQLConnector();
    }
}
