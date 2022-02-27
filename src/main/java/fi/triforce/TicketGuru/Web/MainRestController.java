package fi.triforce.TicketGuru.Web;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fi.triforce.TicketGuru.Domain.Event;
import fi.triforce.TicketGuru.Domain.EventRepository;
import fi.triforce.TicketGuru.Domain.Venue;
import fi.triforce.TicketGuru.Domain.VenueRepository;

@RestController
@RequestMapping("/api")
public class MainRestController {

	@Autowired
	private EventRepository er;

	@Autowired
	private VenueRepository vr;

	@GetMapping("/events")
	public List<Event> eventListRest() {
		return (List<Event>) er.findAll();
	}

	// Events

	@GetMapping("/events/{id}")
	public ResponseEntity<Event> eventGetSingleRest(@PathVariable(name = "id") Long id)
			throws ResourceNotFoundException {
		Event event = er.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
		return ResponseEntity.ok(event);
	}

	@PostMapping("/events")
	public ResponseEntity<Event> eventPostRest(@RequestBody Event event) {
		event.setDate(LocalDate.now());
		return ResponseEntity.ok(er.save(event));
	}

	@DeleteMapping("/events/{id}")
	@ResponseBody
	public String eventDeleteSingleRest(@PathVariable(name = "id") Long id)
			throws ResourceNotFoundException {
		Event event = er.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
		er.delete(event);
		return "Deleted " + event.getEventDescription();
	}

	@PutMapping("/events/{id}")
	public ResponseEntity<Event> eventUpdateSingleRest(@PathVariable(name = "id") Long id,
			@Valid @RequestBody Event newEvent)
			throws ResourceNotFoundException {
		Event event = er.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
		event.setEventDescription(newEvent.getEventDescription());
		event.setDate(newEvent.getDate());
		event.setNumberOfTickets(newEvent.getNumberOfTickets());
		event.setEventVenue(newEvent.getEventVenue());
		return ResponseEntity.ok(er.save(event));
	}

	// Venues

	@GetMapping("/venues")
	public List<Venue> venueListRest() {
		return (List<Venue>) vr.findAll();
	}

    @GetMapping("/venues/{id}")
	public ResponseEntity<Venue> venueGetSingleRest(@PathVariable(name = "id") Long id)
			throws ResourceNotFoundException {
		Venue venue = vr.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find a venue with the id " + id));
		return ResponseEntity.ok(venue);
	}

    @PostMapping("/venues")
	public ResponseEntity<Venue> venuePostRest(@RequestBody Venue venue) {
		return ResponseEntity.ok(vr.save(venue));
	}

}
