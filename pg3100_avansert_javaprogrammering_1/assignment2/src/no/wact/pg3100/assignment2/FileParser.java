package no.wact.pg3100.assignment2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class FileParser {

    private int attributesPrLine;
    private String filename;
    private List<DBColumn> columnList;
    private List<DBRow> rowList;

    public FileParser(String filename) {
        if (filename == null || filename.length() == 0) {
            throw new IllegalArgumentException("The file must not be null or an empty string.");
        }
        this.filename = filename;
        columnList = new ArrayList<>();
        rowList = new ArrayList<>();
    }

    public DBTable parseFile() throws Exception {
        DBTable table = new DBTable();
        try (Scanner scanner = new Scanner(new File(filename))) {
            // read the first three lines containing metadata
            parseMetaData(scanner.nextLine().split("/"), scanner.nextLine().split("/"), scanner.nextLine().split("/"));
            table.setColumns(columnList);
            while (scanner.hasNext()) {
                parseAttributes(scanner.nextLine().split("/"));
            }
            table.setRows(rowList);
        }
        return table;
    }

    private void parseMetaData(String[] columnNames, String[] columnTypes, String[] columnLengths) throws Exception {
        if (columnNames.length != columnTypes.length || columnNames.length != columnLengths.length) {
            throw new IllegalArgumentException("All metadata arrays must be of the same length");
        }
        attributesPrLine = columnNames.length;
        for (int i = 0; i < columnNames.length; i++) {
            columnList.add(new DBColumn(columnNames[i], convertDataType(columnTypes[i]), Integer.parseInt(columnLengths[i])));
        }
    }

    private void parseAttributes(String[] attributes) {
        if (attributesPrLine != attributes.length) {
            throw new IllegalArgumentException("Your file does not follow the required formatting.");
        }
        DBRow row = new DBRow();
        for (int i = 0; i < attributes.length; i++) {
            if (columnList.get(i).getDatatype().equals("INT") && !isDigit(attributes[i])) {
                throw new IllegalArgumentException("Specified INT but datatype could not be converted to INT");
            }
            String key = columnList.get(i).getName();
            row.addAttribute(key, attributes[i]);
        }
        rowList.add(row);
    }

    private boolean isDigit(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String convertDataType(String datatype) throws Exception {
        switch (datatype) {
            case "INT": return "INT";
            case "STRING": return "VARCHAR";
            default: throw new IllegalArgumentException("Unknown datatype. Might need to be specified.");
        }
    }
}
