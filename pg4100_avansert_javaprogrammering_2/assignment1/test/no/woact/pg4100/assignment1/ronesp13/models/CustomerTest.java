package no.woact.pg4100.assignment1.ronesp13.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = new Customer(null, null, "Random");
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Random", customer.getName());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("Random", customer.toString());
    }
}