package no.woact.dao;

import no.woact.model.Event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
@Stateless
public class EventDao implements Serializable {

    @PersistenceContext(unitName = "pg6100_exam")
    private EntityManager entityManager;

    public List<Event> getAll() {
        return entityManager.createNamedQuery(Event.GET_ALL, Event.class).getResultList();
    }

    public List<Event> getByCountry(String country) {
        return entityManager.createNamedQuery(Event.GET_BY_COUNTRY, Event.class).setParameter("country", country).getResultList();
    }

    public Event getById(Long id) {
        return entityManager.find(Event.class, id);
    }

    public Event getByTitle(String title) {
        try {
            return entityManager.createNamedQuery(Event.GET_BY_TITLE, Event.class).setParameter("title", title).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Event persist(Event event) {
        entityManager.persist(event);
        return event;
    }

    public Event update(Event event) {
        if (!entityManager.contains(event)) {
            event = entityManager.merge(event);
            return event;
        }
        return null;
    }
}
