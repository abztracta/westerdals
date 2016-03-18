package no.wact.pg3100.assignment2;

import java.util.HashMap;
import java.util.Map;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class DBRow {

    private Map<String, String> attributes;

    public DBRow() {
        attributes = new HashMap<>();
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
