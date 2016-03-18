package no.nith.pg5100.dto;

import no.nith.pg5100.dto.constraint.ValidStartEndDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SecondaryTable;
import javax.persistence.SequenceGenerator;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@SecondaryTable(name = "event_details")
@NamedQuery(name = "Event.getAll", query = "select e from Event e order by e.startDate desc")
@SequenceGenerator(name = "SEQ_EVENT", initialValue = 50)
@ValidStartEndDate
public class Event {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "SEQ_EVENT")
    private int id;
    @NotNull(message = "An event must have a type")
    @Enumerated(STRING)
    private EventType type;
    @NotNull
    @Size(min = 5, max = 25, message = "The title must contain minimum {min} and maximum {max} characters")
    private String title;
    @Size(max = 100, message = "The description must be less than {max} characters")
    private String description;
    @NotNull(message = "An event must be associated with a subject")
    @Valid
    @ManyToOne
    @JoinColumn(name = "FK_SUBJECT")
    private Subject subject;
    @NotNull(message = "An event must have a start date")
    @Column(table = "event_details")
    private Date startDate;
    @NotNull(message = "An event must have a end date")
    @Column(table = "event_details")
    private Date endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
