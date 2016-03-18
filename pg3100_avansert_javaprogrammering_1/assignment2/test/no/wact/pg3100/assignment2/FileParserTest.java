package no.wact.pg3100.assignment2;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FileParserTest {

    @Test
    public void testParseFileWithValidInput() throws Exception {
        FileParser parser = new FileParser("tekstfil_test_valid.txt");
        DBTable table = parser.parseFile();
        assertNotNull(table);
        assertNull(table.getTablename());
        assertEquals(4, table.getColumns().size());
        assertEquals("name", table.getColumns().get(0).getName());
        assertEquals("VARCHAR", table.getColumns().get(0).getDatatype());
        assertEquals(128, table.getColumns().get(0).getLength());
        assertEquals("subjects", table.getColumns().get(1).getName());
        assertEquals("INT", table.getColumns().get(1).getDatatype());
        assertEquals(3, table.getColumns().get(1).getLength());
        assertEquals("email", table.getColumns().get(2).getName());
        assertEquals("VARCHAR", table.getColumns().get(2).getDatatype());
        assertEquals(128, table.getColumns().get(2).getLength());
        assertEquals("position", table.getColumns().get(3).getName());
        assertEquals("VARCHAR", table.getColumns().get(3).getDatatype());
        assertEquals(128, table.getColumns().get(3).getLength());
        assertEquals(3, table.getRows().size());
    }

    @Test(expected = FileNotFoundException.class)
    public void testParseFileWhichDoesNotExist() throws Exception {
        FileParser parser = new FileParser("afilewhichdoesnotexist.gg");
        parser.parseFile();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFileWithInvalidInput() throws Exception {
        FileParser parser = new FileParser("tekstfil_test_invalid_data_string_instead_of_int.txt");
        parser.parseFile();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFileWithInvalidDatatype() throws Exception {
        FileParser parser = new FileParser("tekstfil_test_invalid_datatype.txt");
        parser.parseFile();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFileWithInvalidAmountOfAttributes() throws Exception {
        FileParser parser = new FileParser("tekstfil_test_invalid_data_invalid_amount_of_attributes.txt");
        parser.parseFile();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFileWithInvalidMetaData() throws Exception {
        FileParser parser = new FileParser("tekstfil_test_invalid_metadata.txt");
        parser.parseFile();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorFail() throws Exception {
        new FileParser("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullInput() throws Exception {
        new FileParser(null);
    }
}