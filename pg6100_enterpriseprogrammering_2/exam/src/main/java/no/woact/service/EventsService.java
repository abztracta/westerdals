package no.woact.service;

import no.woact.dao.EventDao;
import no.woact.model.Event;
import no.woact.service.dto.EventListDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
@Path("/events")
@Stateless
@Produces({ "application/json", "application/xml" })
@Consumes({ "application/json", "application/xml" })
public class EventsService implements Serializable {

    @Inject
    private EventDao eventDao;

    @GET
    @Path("/all")
    public Response getAll(@QueryParam("country") String country, @QueryParam("attendees") boolean attendees) {
        if (country == null) {
            List<Event> events = eventDao.getAll();
            events.stream().forEach(e -> {
                if (attendees) {
                    e.setParticipants(e.getUsers());
                }
            });
            return Response.ok(new EventListDto(events)).build();
        } else {
            List<Event> events = eventDao.getByCountry(country);
            events.stream().forEach(e -> {
                if (attendees) {
                    e.setParticipants(e.getUsers());
                }
            });
            if (events.size() == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.ok(new EventListDto(events)).build();
        }
    }

    @GET
    public Response getEvent(@QueryParam("id") Long id, @QueryParam("attendees") boolean attendees) {
        Event event = eventDao.getById(id);
        if (event == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (attendees) {
            event.setParticipants(event.getUsers());
        }
        return Response.ok(event).build();
    }
}
