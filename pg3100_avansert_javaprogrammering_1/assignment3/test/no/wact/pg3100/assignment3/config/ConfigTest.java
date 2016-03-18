package no.wact.pg3100.assignment3.config;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest {

    @Test
    public void testConstructor() {
        Config config = new Config();
        assertNotNull(config);
    }
}