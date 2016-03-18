package no.woact.pg4100.assignment1.ronesp13.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RentalCarTest {

    private Customer customer;
    private RentalCar rentalCar;

    @Before
    public void setUp() throws Exception {
        customer = new Customer(null, null, "Test Customer");
        rentalCar = new RentalCar("TE57337");
    }

    @Test
    public void testSetCustomer() throws Exception {
        rentalCar.setCustomer(customer);
        assertNotNull(rentalCar.getCustomer());
        assertEquals("Test Customer", rentalCar.getCustomer().getName());
    }

    @Test
    public void testGetCustomer() throws Exception {
        rentalCar.setCustomer(null);
        assertNull(rentalCar.getCustomer());
        rentalCar.setCustomer(customer);
        assertEquals("Test Customer", rentalCar.getCustomer().getName());

    }

    @Test
    public void testGetRegistrationNumber() throws Exception {
        assertEquals("TE57337", rentalCar.getRegistrationNumber());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("TE57337", rentalCar.toString());
    }
}