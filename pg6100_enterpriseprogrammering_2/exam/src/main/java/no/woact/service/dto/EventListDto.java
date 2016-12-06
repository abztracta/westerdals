package no.woact.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import no.woact.model.Event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
@XmlRootElement(name = "EventListDto")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class EventListDto {

    @XmlElement(name = "EventDto")
    @JsonProperty(value = "events")
    private List<Event> events;

    public EventListDto() {}

    public EventListDto(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
