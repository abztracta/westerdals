package no.woact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static no.woact.model.Event.GET_ALL;
import static no.woact.model.Event.GET_BY_COUNTRY;
import static no.woact.model.Event.GET_BY_TITLE;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
@Entity
@Table(name = "events")
@NamedQueries({
        @NamedQuery(name = GET_ALL, query = "SELECT event FROM Event event"),
        @NamedQuery(name = GET_BY_COUNTRY, query = "SELECT event FROM Event event WHERE country = :country"),
        @NamedQuery(name = GET_BY_TITLE, query = "SELECT event FROM Event event WHERE title = :title")
})
@XmlRootElement(name = "EventDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class Event implements Serializable {

    public static final String GET_BY_COUNTRY = "GET_BY_COUNTRY";
    public static final String GET_ALL = "GET_ALL";
    public static final String GET_BY_TITLE = "GET_BY_TITLE";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlElement(name = "Id")
    @JsonProperty(value = "id")
    private Long id;
    @NotNull
    @XmlElement(name = "Title")
    @JsonProperty(value = "title")
    private String title;
    @NotNull
    @XmlElement(name = "Country")
    @JsonProperty(value = "country")
    private String country;
    @NotNull
    @XmlElement(name = "Location")
    @JsonProperty(value = "location")
    private String location;
    @NotNull
    @XmlElement(name = "Description")
    @JsonProperty(value = "description")
    private String description;

    @XmlTransient
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "events_users", joinColumns = {
            @JoinColumn(name = "event_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "user_id",
                    nullable = false, updatable = false) })
    private List<User> users = new ArrayList<>();

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("users")
    @XmlElement(name = "UserDto")
    private List<User> participants;

    public Event() {}

    public Event(String title) {
        this.title = title;
    }

    public Event(String title, String country, String location, String description) {
        this.title = title;
        this.country = country;
        this.location = location;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public boolean isUserAttending(User user) {
        return this.users.stream().anyMatch(u -> u.getId().equals(user.getId()));
    }

    public void addAttendee(User user) {
        this.users.add(user);
    }

    public void removeAttendee(User user) {
        this.users.removeIf(attendingUser -> attendingUser.getId().equals(user.getId()));
    }

    @JsonIgnore
    @XmlTransient
    public int getParticipantCount() {
        return users.size();
    }
}
