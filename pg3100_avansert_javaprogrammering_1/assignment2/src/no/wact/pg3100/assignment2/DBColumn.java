package no.wact.pg3100.assignment2;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class DBColumn {

    private String name;
    private String datatype;
    private int length;

    public DBColumn(String name, String datatype, int length) {
        setName(name);
        setDatatype(datatype);
        setLength(length);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
