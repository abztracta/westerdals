package no.woact.pg4100.assignment1.ronesp13.util;

import java.util.Random;

public class Rand {

    private static Random random;

    static {
        random = new Random();
    }

    /**
     *
     * @param lowerBounds inclusive
     * @param upperBounds exclusive
     * @return random value between lowerBounds and upperBounds
     */
    public static int nextInt(int lowerBounds, int upperBounds) {
        return random.nextInt(upperBounds) + lowerBounds;
    }
}
