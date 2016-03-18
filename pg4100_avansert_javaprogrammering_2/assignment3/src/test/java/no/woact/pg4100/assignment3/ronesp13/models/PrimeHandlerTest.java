package no.woact.pg4100.assignment3.ronesp13.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrimeHandlerTest {

    private PrimeHandler prime;

    @Test
    public void testPrime() {
        prime = new PrimeHandler("7");
        assertTrue(prime.isInteger());
        assertTrue(prime.isPrime());
    }

    @Test
    public void testNotAPrime() throws Exception {
        prime = new PrimeHandler("8");
        assertTrue(prime.isInteger());
        assertFalse(prime.isPrime());
    }

    @Test
    public void testError() throws Exception {
        prime = new PrimeHandler("e");
        assertFalse(prime.isInteger());
        assertFalse(prime.isPrime());
    }

    @Test
    public void testNullInput() throws Exception {
        prime = new PrimeHandler(null);
        assertFalse(prime.isInteger());
        assertFalse(prime.isPrime());
    }
}