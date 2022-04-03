package fi.triforce.TicketGuru.Web.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import fi.triforce.TicketGuru.Domain.Event;
import fi.triforce.TicketGuru.Domain.EventRepository;
import fi.triforce.TicketGuru.Domain.SalesEvent;
import fi.triforce.TicketGuru.Domain.SalesEventRepository;
import fi.triforce.TicketGuru.Domain.TicketType;
import fi.triforce.TicketGuru.Domain.TicketTypeRepository;
import fi.triforce.TicketGuru.Domain.Venue;
import fi.triforce.TicketGuru.Domain.VenueRepository;
import fi.triforce.TicketGuru.exception.ResourceNotFoundException;
import fi.triforce.TicketGuru.exception.ValidationException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import fi.triforce.TicketGuru.utils.ReturnMsg;

@Service
public class EventService {

    @Autowired
    private EventRepository er;

    @Autowired
    private VenueRepository vr;

    @Autowired
    private TicketTypeRepository ttr;

    @Autowired
    private SalesEventRepository sr;

    @Autowired
    private Validator validator;

    // Event services

    public List<Event> getAllEvents() {
        return (List<Event>) er.findAll();
    }

    public Event getSingleEvent(Long id) {
        Event event = er.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
        return event;
    }

    public Event addEvent(Event event) {
        Set<ConstraintViolation<Event>> result = validator.validate(event);
        if (!result.isEmpty()) {
            String errorMsg = result.toString();
            String[] splitMsg = errorMsg.split("=");
            String propertyName = splitMsg[2].split(",")[0] + " " + splitMsg[1].split(",")[0].replace("'", "");
            throw new ValidationException(propertyName);
        }
        if (event.getEventVenue() != null) {
            Long venueId = event.getEventVenue().getVenueId();
            Venue venue = vr.findById(venueId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot find a venue with the id " + venueId));
            event.setEventVenue(venue);
        }
        return er.save(event);
    }

    public HashMap<String, String> deleteEvent(Long id) {
        Event event = er.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
        er.delete(event);
        return new ReturnMsg("Deleted an event with the id " + id).getReturnMsg();
    }

    public Event updateEvent(Long id, Event newEvent) {
        Event event = er.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
        Set<ConstraintViolation<Event>> result = validator.validate(newEvent);
        if (!result.isEmpty()) {
            String errorMsg = result.toString();
            String[] splitMsg = errorMsg.split("=");
            String propertyName = splitMsg[2].split(",")[0] + " " + splitMsg[1].split(",")[0].replace("'", "");
            throw new ValidationException(propertyName);
        }
        if (newEvent.getEventVenue() != null) {
            Long venueId = newEvent.getEventVenue().getVenueId();
            Venue venue = vr.findById(venueId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot find a venue with the id " + venueId));
            event.setEventVenue(venue);
        }
        event.setEventTitle(newEvent.getEventTitle());
        event.setEventDescription(newEvent.getEventDescription());
        event.setDateOfEvent(newEvent.getDateOfEvent());
        event.setNumberOfTickets(newEvent.getNumberOfTickets());
        return er.save(event);
    }

    // Event tickettype services

    public List<TicketType> getEventTicketTypes(Long id) {
        Event event = er.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
        return (List<TicketType>) ttr.findByEvent(event);
    }

    public TicketType getTicketType(Long eventId, Long ttId) {
        er.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + eventId));
        TicketType ticketType = ttr.findById(ttId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find a tickettype with the id " + ttId));
        return ticketType;
    }

    public TicketType addTicketType(Long eventId, TicketType newType) {
        Event event = er.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + eventId));
        Set<ConstraintViolation<TicketType>> result = validator.validate(newType); // tästä alkavan snippetin vois
                                                                                   // melkeen muuttaa omaks funktioks,
                                                                                   // koska yleiskäyttönen
        if (!result.isEmpty()) {
            String errorMsg = result.toString();
            String[] splitMsg = errorMsg.split("=");
            String propertyName = splitMsg[2].split(",")[0] + " " + splitMsg[1].split(",")[0].replace("'", "");
            throw new ValidationException(propertyName);
        }
        newType.setEvent(event);
        return ttr.save(newType);
    }


    public HashMap<String, String> deleteTicketType(Long eventId, Long ttId) {
        er.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + eventId));
        TicketType ticketType = ttr.findById(ttId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Cannot find a tickettype with the id " + ttId));
        ttr.delete(ticketType);
        return new ReturnMsg("Deleted a tickettype with the id " + ttId).getReturnMsg();
    }

    public TicketType updateTicketType(Long eventId, Long ttId, TicketType newType) {
        er.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + eventId));
        TicketType ticketType = ttr.findById(ttId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Cannot find a tickettype with the id " + ttId));
        Set<ConstraintViolation<TicketType>> result = validator.validate(newType);
        if (!result.isEmpty()) {
            String errorMsg = result.toString();
            String[] splitMsg = errorMsg.split("=");
            String propertyName = splitMsg[2].split(",")[0] + " " + splitMsg[1].split(",")[0].replace("'", "");
            throw new ValidationException(propertyName);
        }
        ticketType.setPrice(newType.getPrice());
        ticketType.setTicketTypeDescription(newType.getTicketTypeDescription());
        return ttr.save(ticketType);
    }

    // Salesevent listing service(s)

    public List<SalesEvent> getSalesEvents(Long eventId) {
        return sr.getAllSalesEventsByEvent(eventId);
    }
}
