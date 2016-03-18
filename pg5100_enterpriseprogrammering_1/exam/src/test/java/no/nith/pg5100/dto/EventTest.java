package no.nith.pg5100.dto;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.Set;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class EventTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testNullEvent() throws Exception {
        Event event = new Event();

        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        assertEquals(6, violations.size());
    }

    @Test
    public void testInvalidEvent() throws Exception {
        Event event = new Event();
        Subject subject = new Subject();
        event.setTitle("Cake");
        event.setSubject(subject);
        event.setType(EventType.LECTURE);
        Calendar startDate = Calendar.getInstance();
        startDate.set(2016, Calendar.JANUARY, 1, 18, 0);
        event.setStartDate(startDate.getTime());
        Calendar endDate = Calendar.getInstance();
        endDate.set(2015, Calendar.JANUARY, 1, 20, 0);
        event.setEndDate(endDate.getTime());

        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        assertEquals(3, violations.size());
    }

    @Test
    public void testValidEvent() throws Exception {
        Event event = new Event();
        Subject subject = new Subject();
        subject.setId(1);
        subject.setName("PG1337");
        event.setTitle("Lecture #1");
        event.setType(EventType.LECTURE);
        event.setDescription("Learn Java EE");
        event.setSubject(subject);
        Calendar startDate = Calendar.getInstance();
        startDate.set(2016, Calendar.JANUARY, 1, 18, 0);
        event.setStartDate(startDate.getTime());
        Calendar endDate = Calendar.getInstance();
        endDate.set(2016, Calendar.JANUARY, 1, 20, 0);
        event.setEndDate(endDate.getTime());

        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        assertTrue(violations.isEmpty());
    }
}