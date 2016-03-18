package no.nith.pg5100.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQuery(name = "Location.getAll", query = "select l from Location l")
@SequenceGenerator(name = "SEQ_LOCATION", initialValue = 50)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOCATION")
    private int id;

    @NotNull(message = "A location must have a room number")
    @Size(min = 1, message = "A location must have a room name")
    private String room;
    @Size(min = 1, message = "A location must have a building name")
    @NotNull(message = "A location must have a building name")
    private String building;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}
