package no.nith.pg5100.infrastructure.event;

import no.nith.pg5100.dto.Event;
import no.nith.pg5100.dto.EventType;
import no.nith.pg5100.dto.Subject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JpaEventDaoTest {

    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private JpaEventDao eventDao;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("pg5100-lms");
        entityManager = factory.createEntityManager();
        eventDao = new JpaEventDao(entityManager);
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
        factory.close();
    }

    @Test
    public void testPersist() throws Exception {
        Subject subject = new Subject();
        subject.setId(1);

        Event event = new Event();
        event.setType(EventType.LECTURE);
        event.setTitle("PG5100 Lecture");
        event.setStartDate(Date.from(Instant.now()));
        event.setEndDate(Date.from(Instant.now().plusSeconds(14 * 24 * 60 * 60)));
        event.setSubject(subject);

        entityManager.getTransaction().begin();
        Event result = eventDao.persist(event);
        entityManager.getTransaction().commit();
        assertTrue(result.getId() > 0);
    }

    @Test
    public void testFindById() throws Exception {
        Event event = eventDao.findById(1);
        assertEquals("Lecture #1", event.getTitle());
    }

    @Test
    public void testGetAll() throws Exception {
        List<Event> events = eventDao.getAll();
        assertEquals(2, events.size());
    }
}