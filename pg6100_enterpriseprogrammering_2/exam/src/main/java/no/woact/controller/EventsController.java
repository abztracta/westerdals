package no.woact.controller;

import no.woact.dao.EventDao;
import no.woact.model.Event;
import no.woact.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
@Model
public class EventsController implements Serializable {

    private static final Logger LOG = Logger.getLogger(EventsController.class.getName());

    @Inject
    private EventDao eventDao;

    private Event event;
    private List<Event> events;

    @PostConstruct
    public void init() {
        event = new Event();
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Event> getAllEvents(String country, boolean onlyOneCountry) {
        if (events != null) {
            return events;
        }
        if (country != null && onlyOneCountry) {
            events = eventDao.getByCountry(country);
        } else {
            events = eventDao.getAll();
        }
        return events;
    }

    public String createNewEvent() {
        Event newEvent = eventDao.persist(event);
        if (newEvent != null) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("success", "You have successfully created an event");
            return "home.xhtml?faces-redirect=true";
        }
        return "newEvent.xhtml";
    }

    public void attendanceButtonClicked(ValueChangeEvent e) {
        UIInput input = (UIInput) e.getSource();
        Map<String, Object> attributes = input.getAttributes();
        Event event = (Event) attributes.get("event");
        User user = (User) attributes.get("user");
        if (event.isUserAttending(user)) {
            event.removeAttendee(user);
        } else {
            event.addAttendee(user);
        }
        eventDao.update(event);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
        } catch (IOException ex) {
            LOG.info("Something went wrong with JSF. " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
