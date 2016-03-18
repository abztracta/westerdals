package no.woact.pg4100.assignment3.ronesp13.models;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.math3.primes.Primes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is used to determine if a value is a prime number or not.
 *
 * @author Espen Rønning
 */
public class PrimeHandler {

    private static final Logger logger = LogManager.getLogger(PrimeHandler.class.getName());

    private boolean isInteger;
    private boolean isPrime;

    /**
     * Takes in a parameter and determines if it is a prime number.
     *
     * @param value the number to check
     */
    public PrimeHandler(String value) {
        if (!isInteger(value)) {
            return;
        }
        isPrime = Primes.isPrime(Integer.parseInt(value));
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return isInteger = true;
        } catch (NumberFormatException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return false;
        }
    }

    /**
     *
     * @return <code>true</code> if the value taken in by the constructor is a integer
     */
    public boolean isInteger() {
        return isInteger;
    }

    /**
     *
     * @return <code>true</code> if the value taken in by the constructor is a prime number
     */
    public boolean isPrime() {
        return isPrime;
    }
}
