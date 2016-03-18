package no.nith.pg5100.controller;

import no.nith.pg5100.dto.Event;
import no.nith.pg5100.dto.EventType;
import no.nith.pg5100.dto.Subject;
import no.nith.pg5100.infrastructure.event.EventDao;
import no.nith.pg5100.infrastructure.subject.SubjectDao;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Model
public class EventController {

    @Inject
    private EventDao eventDao;
    @Inject
    private SubjectDao subjectDao;
    private FacesContext context;
    private Flash flash;

    private int eventId;
    private Event event;
    private int subjectId;

    @PostConstruct
    public void init() {
        event = new Event();
        context = FacesContext.getCurrentInstance();
        flash = context.getExternalContext().getFlash();
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void initEvent() {
        event = eventDao.findById(eventId);
    }

    public List<Event> getAll() {
        return eventDao.getAll();
    }

    public void persist() throws IOException {
        Subject subject = subjectDao.findById(subjectId);
        event.setSubject(subject);
        if (isDatesValid(event)) {
            flash.put("created", "Event created successfully");
            eventDao.persist(event);
            context.getExternalContext().redirect(context.getExternalContext().getApplicationContextPath() + "/event/events.jsf");
        }
    }

    public List<SelectItem> getEventTypes() {
        return Arrays.asList(EventType.values()).stream().map(t -> new SelectItem(t, t.getLabel())).collect(Collectors.toList());
    }

    public List<SelectItem> getSubjects() {
        return subjectDao.getAll().stream().map(t -> new SelectItem(t.getId(), t.getName())).collect(Collectors.toList());
    }

    private boolean isDatesValid(Event event) throws ValidationException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        if (!violations.isEmpty()) {
            violations.forEach(m -> FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, m.getMessage(), m.getMessage())));
        }
        return violations.isEmpty();
    }
}
