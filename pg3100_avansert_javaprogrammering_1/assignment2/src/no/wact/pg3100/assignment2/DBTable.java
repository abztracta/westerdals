package no.wact.pg3100.assignment2;

import java.util.List;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class DBTable {

    private String tablename;
    private List<DBColumn> columns;
    private List<DBRow> rows;

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public List<DBColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DBColumn> columns) {
        this.columns = columns;
    }

    public List<DBRow> getRows() {
        return rows;
    }

    public void setRows(List<DBRow> rows) {
        this.rows = rows;
    }
}
