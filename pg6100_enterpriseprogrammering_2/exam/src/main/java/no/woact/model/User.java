package no.woact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static no.woact.model.User.GET_BY_USERNAME;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
@Entity
@Table(name = "users")
@NamedQuery(name = GET_BY_USERNAME, query = "SELECT user FROM User user WHERE username = :username")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

    public static final String GET_BY_USERNAME = "GET_BY_USERNAME";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlElement(name = "Id")
    @JsonProperty(value = "id")
    private Long id;
    @NotNull
    @Column(unique = true)
    @XmlElement(name = "Username")
    @JsonProperty(value = "username")
    private String username;
    @Transient
    @XmlTransient
    private String password;
    @Transient
    @XmlTransient
    private String passwordConfirmation;
    @NotNull
    @XmlTransient
    private String encryptedPassword;
    @NotNull
    @XmlElement(name = "FirstName")
    @JsonProperty(value = "firstName")
    private String firstName;
    @XmlElement(name = "MiddleName")
    @JsonProperty(value = "middleName")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String middleName;
    @NotNull
    @XmlElement(name = "LastName")
    @JsonProperty(value = "lastName")
    private String lastName;
    @NotNull
    @XmlTransient
    private String country;

    @XmlTransient
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private List<Event> events = new ArrayList<>(0);

    public User() {}

    public User(String username, String password, String passwordConfirmation, String firstName, String middleName, String lastName, String country) {
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    @JsonIgnore
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonIgnore
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonIgnore
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @JsonIgnore
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public boolean attendingEvent(Event event) {
        return events.stream()
                .anyMatch(attendingEvent -> attendingEvent.getTitle().equals(event.getTitle()));
    }

    public boolean isCorrectPassword(String password) {
        return BCrypt.checkpw(password, encryptedPassword);
    }

    public boolean matchingPasswordConfirmation() {
        return !(password == null || passwordConfirmation == null) && password.equals(passwordConfirmation);
    }
}
