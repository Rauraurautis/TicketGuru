package fi.triforce.TicketGuru.Web.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import fi.triforce.TicketGuru.Domain.Venue;
import fi.triforce.TicketGuru.Domain.VenueRepository;
import fi.triforce.TicketGuru.exception.ResourceNotFoundException;
import fi.triforce.TicketGuru.utils.EntityValidation;
import fi.triforce.TicketGuru.utils.ReturnMsg;

@Service
public class VenueService {

    @Autowired
    private VenueRepository vr;

    @Autowired
    private Validator validator;

    public List<Venue> getAllVenues() {
        return (List<Venue>) vr.findAll();
    }

    public Venue getSingleVenue(Long venueId) {
        Venue venue = vr.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find a venue with the id " + venueId));
        return venue;
    }

    public Venue postVenue(Venue venue) {
        EntityValidation.validateEntity(validator, venue);
        return vr.save(venue);
    }

    public HashMap<String, String> deleteVenue(Long venueId) {
        Venue venue = vr.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find a venue with the id " + venueId));
        vr.delete(venue);
        return new ReturnMsg("Deleted a venue with the id " + venueId).getReturnMsg();
    }

    public Venue updateVenue(Long venueId, Venue editedVenue) {
        Venue venue = vr.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find a venue with the id " + venueId));
        EntityValidation.validateEntity(validator, editedVenue);
        venue.setVenueName(editedVenue.getVenueName());
        venue.setVenueAddress(editedVenue.getVenueAddress());
        venue.setVenueCity(editedVenue.getVenueCity());
        return vr.save(venue);
    }

}
