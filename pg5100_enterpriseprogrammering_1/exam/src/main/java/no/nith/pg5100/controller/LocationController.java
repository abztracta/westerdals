package no.nith.pg5100.controller;

import no.nith.pg5100.dto.Location;
import no.nith.pg5100.infrastructure.location.LocationDao;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import java.io.IOException;

@Model
public class LocationController {
    @Inject
    private LocationDao locationDao;
    private Location location;
    private FacesContext context;
    private Flash flash;

    @PostConstruct
    public void init() {
        location = new Location();
        context = FacesContext.getCurrentInstance();
        flash = context.getExternalContext().getFlash();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void persist() throws IOException {
        if (locationDao.persist(location) != null) {
            flash.put("created", "Location created successfully");
            context.getExternalContext().redirect(context.getExternalContext().getApplicationContextPath() + "/location/location-registration.jsf");
        }
    }
}
