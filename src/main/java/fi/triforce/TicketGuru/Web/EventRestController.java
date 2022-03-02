package fi.triforce.TicketGuru.Web;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.triforce.TicketGuru.Domain.Event;
import fi.triforce.TicketGuru.Domain.EventRepository;
import fi.triforce.TicketGuru.Domain.Venue;
import fi.triforce.TicketGuru.Domain.VenueRepository;

@RestController
@RequestMapping("/api/events")
public class EventRestController {

	@Autowired
	private EventRepository er;

	@Autowired
	private VenueRepository vr;

	@GetMapping
	public List<Event> eventListRest() {
		return (List<Event>) er.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Event> eventGetSingleRest(@PathVariable(name = "id") Long id)
			throws ResourceNotFoundException {
		Event event = er.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
		return ResponseEntity.ok(event);
	}

	@PostMapping
	public ResponseEntity<Event> eventPostRest(@RequestBody Event event) {
		Long venueId = event.getEventVenue().getVenueId();
		Venue venue = vr.findById(venueId).orElse(null);
		event.setEventVenue(venue);
		return ResponseEntity.ok(er.save(event));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eventDeleteSingleRest(@PathVariable(name = "id") Long id)
			throws ResourceNotFoundException {
		Event event = er.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
		er.delete(event);
		HashMap<String, String> returnMsg = new HashMap<String, String>();
		returnMsg.put("message", "Deleted an event with the id " + id);
		return ResponseEntity.ok(returnMsg);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Event> eventUpdateSingleRest(@PathVariable(name = "id") Long id,
			@Valid @RequestBody Event newEvent)
			throws ResourceNotFoundException {
		Event event = er.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
		System.out.println(newEvent.getEventVenue());
		Long venueId = newEvent.getEventVenue().getVenueId();
		Venue venue = vr.findById(venueId).orElse(null);
		event.setEventVenue(venue);
		event.setEventTitle(newEvent.getEventTitle());
		event.setEventDescription(newEvent.getEventDescription());
		event.setDateOfEvent(newEvent.getDateOfEvent());
		event.setNumberOfTickets(newEvent.getNumberOfTickets());
		return ResponseEntity.ok(er.save(event));
	}

}
