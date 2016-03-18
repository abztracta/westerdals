package no.wact.pg3100.assignment2;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Espen Rønning - ronesp13@student.westerdals.no
 */
public class DBHandler {

    private static final String SELECT_METADATA_STATEMENT =
            "SHOW COLUMNS FROM `:tablename`";
    private static final String DROP_TABLE_STATEMENT =
            "DROP TABLE IF EXISTS `:tablename`";

    private static final String CREATE_TABLE_STATEMENT =
            "CREATE TABLE `:tablename` (" +
                    "uid int auto_increment," +
                    ":attributes" +
                    "primary key (`uid`)" +
                    ");";
    private static final String INSERT_STATEMENT =
            "INSERT INTO `:tablename` (:params) VALUES (:values);";
    private static final String SELECT_STATEMENT =
            "SELECT :attributes FROM :tablename";

    private MySQLConnector db;

    public DBHandler(String username, String password) throws SQLException {
        db = new MySQLConnector(username, password);
    }

    public Connection getConnection() {
        return db.getConnection();
    }

    public void close() throws Exception {
        if (db != null && db.getConnection() != null && !db.getConnection().isClosed()) {
            db.close();
        }
    }

    public void copyFile(String filename, String tablename) {
        if (filename == null || tablename == null || filename.length() == 0 || tablename.length() == 0) {
            System.out.println("Invalid file format.");
            return;
        }
        try {
            dropExistingTable(tablename);
            DBTable table = new FileParser(filename).parseFile();
            table.setTablename(tablename);
            buildTable(table);
            insertData(table);
        } catch (FileNotFoundException e) {
            System.out.println("The specified file does not exist");
        } catch (IllegalArgumentException e) {
            System.out.println("Your datafile is corrupt.");
        } catch (SQLException e) {
            System.out.println("A SQL related error occurred.");
        } catch (Exception e) {
            System.out.println("An unknown error occurred.");
        }
    }

    private void dropExistingTable(String tablename) throws SQLException {
        try (Statement statement = db.getConnection().createStatement()) {
            statement.execute(DROP_TABLE_STATEMENT.replaceFirst(":tablename", tablename));
        }
    }

    private void insertData(DBTable table) throws Exception {
        try (PreparedStatement statement = db.getConnection().prepareStatement(buildInsertStatement(table))) {
            db.getConnection().setAutoCommit(false);
            addParamsToInsertStatement(table, statement);
            statement.executeBatch();
            db.getConnection().commit();
        } catch (SQLException e) {
            db.getConnection().rollback();
        }
    }

    private void addParamsToInsertStatement(DBTable table, PreparedStatement statement) throws Exception {
        List<DBColumn> columns = table.getColumns();
        List<DBRow> rows = table.getRows();
        for (DBRow row : rows) {
            int index = 1;
            for (DBColumn column : columns) {
                setParameters(index, column.getDatatype(), column.getName(), row, statement);
                index++;
            }
            statement.addBatch();
        }
    }

    private void setParameters(int index, String datatype, String fieldName, DBRow row, PreparedStatement statement) throws Exception {
        String value = row.getAttribute(fieldName);
        switch (datatype) {
            case "INT":
                statement.setInt(index, Integer.parseInt(value));
                break;
            case "VARCHAR":
                statement.setString(index, value);
                break;
            default:
                throw new Exception("Invalid datatype. Might not be specified");
        }
    }

    private String buildInsertStatement(DBTable table) {
        String sql = INSERT_STATEMENT.replaceFirst(":tablename", table.getTablename());
        List<DBColumn> columns = table.getColumns();
        String params = "";
        String values = "";
        for (int i = 0; i < columns.size(); i++) {
            params += columns.get(i).getName();
            values += "?";
            if (i < columns.size() - 1) {
                params += ",";
                values += ",";
            }
        }
        return sql.replaceFirst(":params", params).replaceFirst(":values", values);
    }

    private void buildTable(DBTable table) throws SQLException {
        try (Statement statement = db.getConnection().createStatement()) {
            statement.executeUpdate(buildCreateTableStatement(table));
        }
    }

    private String buildCreateTableStatement(DBTable table) {
        String attributes = "";
        List<DBColumn> columns = table.getColumns();
        for (DBColumn column : columns) {
            attributes += column.getName() + " " + column.getDatatype() + "(" + column.getLength() + "),";
        }
        return CREATE_TABLE_STATEMENT.replaceFirst(":tablename", table.getTablename()).replaceFirst(":attributes", attributes);
    }

    public void showTable(String tablename) throws SQLException {
        DBTable table = getTable(tablename);
        if (table == null) {
            System.out.println("Could not find table = \"" + tablename + "\"");
            return;
        }
        System.out.println(table.getTablename());
        System.out.println("---");
        printColumns(table.getColumns());
        printRows(table.getColumns(), table.getRows());
    }

    private void printColumns(List<DBColumn> columnList) {
        for (DBColumn column : columnList) {
            System.out.printf("%-20s", column.getName());
        }
        System.out.println();
    }

    private void printRows(List<DBColumn> columnList, List<DBRow> rowList) {
        for (DBRow row : rowList) {
            for (DBColumn column : columnList) {
                System.out.printf("%-20s", row.getAttribute(column.getName()));
            }
            System.out.println();
        }
    }

    public DBTable getTable(String tablename) {
        if (tablename == null || tablename.length() == 0) {
            return null;
        }
        DBTable table = new DBTable();
        table.setTablename(tablename);
        try {
            List<DBColumn> columns = getDBColumns(table.getTablename());
            List<DBRow> rows = getDBRows(table.getTablename(), columns);
            table.setColumns(columns);
            table.setRows(rows);
        } catch (SQLException e) {
            return null;
        }

        return table;
    }

    private List<DBColumn> getDBColumns(String tablename) throws SQLException {
        try (
                Statement statement = db.getConnection().createStatement();
                ResultSet rs = statement.executeQuery(SELECT_METADATA_STATEMENT.replaceFirst(":tablename", tablename))
        ) {
            List<DBColumn> columns = new ArrayList<>();
            while (rs.next()) {
                String field = rs.getString("Field");
                String type = rs.getString("Type");
                columns.add(new DBColumn(field, getDatatypeFromMetaData(type), getDatatypeSizeFromMetaData(type)));
            }
            return columns;
        }
    }

    private int getDatatypeSizeFromMetaData(String field) {
        return Integer.parseInt(field.replaceFirst(".*?\\(([0-9]+)\\).*?", "$1"));
    }

    private String getDatatypeFromMetaData(String field) {
        return field.replaceFirst("\\(.*?\\)", "").toUpperCase().replaceFirst("VARCHAR", "STRING");
    }

    private List<DBRow> getDBRows(String tablename, List<DBColumn> columns) throws SQLException {
        try (
                Statement statement = db.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(buildSelectStatement(tablename, columns))
        ) {
            int count = columns.size();
            List<DBRow> rows = new ArrayList<>();
            while (resultSet.next()) {
                DBRow row = new DBRow();
                for (int i = 1; i <= count; i++) {
                    row.addAttribute(columns.get(i - 1).getName(), resultSet.getObject(i) + "");
                }
                rows.add(row);
            }
            return rows;
        }
    }

    private String buildSelectStatement(String tablename, List<DBColumn> columns) {
        String attributes = "";
        for (int i = 0; i < columns.size(); i++) {
            attributes += columns.get(i).getName();
            if (i < columns.size() - 1) {
                attributes += ",";
            }
        }
        return SELECT_STATEMENT.replaceFirst(":tablename", tablename).replaceFirst(":attributes", attributes);
    }
}
