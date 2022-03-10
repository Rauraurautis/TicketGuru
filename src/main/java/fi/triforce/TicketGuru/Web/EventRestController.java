package fi.triforce.TicketGuru.Web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
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
import fi.triforce.TicketGuru.Domain.SalesEvent;
import fi.triforce.TicketGuru.Domain.SalesEventRepository;
import fi.triforce.TicketGuru.Domain.TicketType;
import fi.triforce.TicketGuru.Domain.TicketTypeRepository;
import fi.triforce.TicketGuru.Domain.Venue;
import fi.triforce.TicketGuru.Domain.VenueRepository;
import fi.triforce.TicketGuru.exception.ResourceNotFoundException;
import fi.triforce.TicketGuru.utils.ReturnMsg;

@RestController
@RequestMapping("/api/events")
public class EventRestController {

	@Autowired
	private EventRepository er;

	@Autowired
	private VenueRepository vr;

	@Autowired
	private TicketTypeRepository ttr;

	@Autowired
	private SalesEventRepository sr;

	// Kaikkien eventtien listaus
	@GetMapping
	public List<Event> eventListRest() {
		return (List<Event>) er.findAll();
	}

	// Yksittäisen eventin tiedot
	@GetMapping("/{id}")
	public ResponseEntity<Event> eventGetSingleRest(@PathVariable(name = "id") Long id)
			throws ResourceNotFoundException {
		Event event = er.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
		return ResponseEntity.ok(event);
	}

	// Eventin lisäys
	@PostMapping
	public ResponseEntity<Event> eventPostRest(@RequestBody Event event) {
		Long venueId = event.getEventVenue().getVenueId();
		Venue venue = vr.findById(venueId).orElse(null);
		event.setEventVenue(venue);
		return new ResponseEntity<>(er.save(event), HttpStatus.CREATED);
	}

	// Event poisto
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eventDeleteSingleRest(@PathVariable(name = "id") Long id)
			throws ResourceNotFoundException {
		Event event = er.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
		er.delete(event);
		return ResponseEntity.ok(new ReturnMsg("Deleted an event with the id " + id).getReturnMsg());
	}

	// Event muokkaus
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

	// Tickettype listaus
	@GetMapping("/{id}/tickettypes")
	public List<TicketType> ticketTypesListRest(@PathVariable(name = "id") Long id)
			throws ResourceNotFoundException {
		Event event = er.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + id));
		return (List<TicketType>) ttr.findByEvent(event);
	}

	// Tickettype lisäys
	@PostMapping("/{id}/tickettypes")
	public ResponseEntity<TicketType> ticketTypePostRest(@PathVariable(name = "id") Long eventId,
			@RequestBody TicketType newType)
			throws ResourceNotFoundException {
		Event event = er.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + eventId));
		newType.setEvent(event);
		return ResponseEntity.ok(ttr.save(newType));
	}

	// Yksittäinen tickettype
	@GetMapping("/{id}/tickettypes/{ttid}")
	public ResponseEntity<TicketType> ticketTypeGetSingleRest(@PathVariable(name = "id") Long eventId,
			@PathVariable(name = "ttid") Long ttId)
			throws ResourceNotFoundException {
		er.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + eventId));
		TicketType ticketType = ttr.findById(ttId)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find a tickettype with the id " + ttId));
		return ResponseEntity.ok(ticketType);
	}

	// Tickettype poisto
	@DeleteMapping("/{id}/tickettypes/{ttid}")
	public ResponseEntity<?> eventDeleteSingleRest(@PathVariable(name = "id") Long eventId,
			@PathVariable(name = "ttid") Long ticketTypeId)
			throws ResourceNotFoundException {
		er.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + eventId));
		TicketType ticketType = ttr.findById(ticketTypeId)
				.orElseThrow(
						() -> new ResourceNotFoundException("Cannot find a tickettype with the id " + ticketTypeId));
		ttr.delete(ticketType);
		return ResponseEntity.ok(new ReturnMsg("Deleted a tickettype with the id " + ticketTypeId).getReturnMsg());
	}

	// Tickettype muokkaus
	@PutMapping("/{id}/tickettypes/{ttid}")
	public ResponseEntity<TicketType> ticketTypeUpdateSingleRest(@PathVariable(name = "id") Long eventId,
			@PathVariable(name = "ttid") Long ticketTypeId,
			@Valid @RequestBody TicketType newType)
			throws ResourceNotFoundException {
		er.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + eventId));
		TicketType ticketType = ttr.findById(ticketTypeId)
				.orElseThrow(
						() -> new ResourceNotFoundException("Cannot find a tickettype with the id " + ticketTypeId));
		ticketType.setPrice(newType.getPrice());
		ticketType.setTicketTypeDescription(newType.getTicketTypeDescription());
		return ResponseEntity.ok(ttr.save(ticketType));
	}

	// Yksittäisen tapahtuman kuittien listaus
	@GetMapping("/{id}/salesevents")
	public List<SalesEvent> getSalesEventsByEvent(@PathVariable(name = "id") Long eventId) {
		return sr.getAllSalesEventsByEvent(eventId);
	}

}
