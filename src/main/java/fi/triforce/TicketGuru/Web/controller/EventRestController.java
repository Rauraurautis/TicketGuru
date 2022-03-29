package fi.triforce.TicketGuru.Web.controller;

import java.util.List;
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

import fi.triforce.TicketGuru.Domain.SalesEvent;

import fi.triforce.TicketGuru.Domain.TicketType;

import fi.triforce.TicketGuru.Web.service.EventService;
import fi.triforce.TicketGuru.exception.ResourceNotFoundException;
import fi.triforce.TicketGuru.exception.ValidationException;


@RestController
@RequestMapping("/api/events")
// @Validated
public class EventRestController {

	@Autowired
	private EventService es;

	// Kaikkien eventtien listaus, ADMIN, SALES
	@GetMapping
	public ResponseEntity<List<Event>> eventListRest() {
		return ResponseEntity.ok(es.getAllEvents());
	}

	// Yksittäisen eventin tiedot, ADMIN, SALES
	@GetMapping("/{id}")
	public ResponseEntity<Event> eventGetSingleRest(@PathVariable(name = "id") Long id)
			throws ResourceNotFoundException {
		return ResponseEntity.ok(es.getSingleEvent(id));
	}

	// Eventin lisäys, ADMIN
	@PostMapping
	public ResponseEntity<Event> eventPostRest(@RequestBody Event event) throws ValidationException {
		return new ResponseEntity<Event>(es.addEvent(event), HttpStatus.CREATED);
	}

	// Event poisto, ADMIN
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eventDeleteSingleRest(@PathVariable(name = "id") Long id)
			throws ResourceNotFoundException {
		return ResponseEntity.ok(es.deleteEvent(id));
	}

	// Event muokkaus, ADMIN
	@PutMapping("/{id}")
	public ResponseEntity<Event> eventUpdateSingleRest(@PathVariable(name = "id") Long id,
			@RequestBody Event newEvent)
			throws ResourceNotFoundException, ValidationException {
		return ResponseEntity.ok(es.updateEvent(id, newEvent));
	}

	// Tickettype listaus, ADMIN, SALES
	@GetMapping("/{id}/tickettypes")
	public ResponseEntity<List<TicketType>> ticketTypesListRest(@PathVariable(name = "id") Long id)
			throws ResourceNotFoundException {
		return ResponseEntity.ok(es.getEventTicketTypes(id));
	}

	// Tickettype lisäys, (String)ticketTypeDescription pakollinen, (float)price ei
	// pakollinen, jolloin -> 0, mutta ei saa olla negatiivinen, ADMIN
	@PostMapping("/{id}/tickettypes")
	public ResponseEntity<TicketType> ticketTypePostRest(@PathVariable(name = "id") Long eventId,
			@RequestBody TicketType newType) // Tästä puuttuu tahallisesti tällä hetkellä @Valid, koska jos se on
												// käytössä, niin erikoissäännöt eivät saa muuta kuin yleisilmoituksen
												// 500. Atm toimii esim. "luku ei saa olla negatiivinen"
			throws ResourceNotFoundException, ValidationException {

		return new ResponseEntity<TicketType>(es.addTicketType(eventId, newType), HttpStatus.CREATED);

	}

	// Yksittäinen tickettype haku, ADMIN, SALES
	@GetMapping("/{id}/tickettypes/{ttid}")
	public ResponseEntity<TicketType> ticketTypeGetSingleRest(@PathVariable(name = "id") Long eventId,
			@PathVariable(name = "ttid") Long ttId)
			throws ResourceNotFoundException {
		return ResponseEntity.ok(es.getTicketType(eventId, ttId));
	}

	// Tickettype poisto, ADMIN
	@DeleteMapping("/{id}/tickettypes/{ttid}")
	public ResponseEntity<?> eventDeleteSingleRest(@PathVariable(name = "id") Long eventId,
			@PathVariable(name = "ttid") Long ttId)
			throws ResourceNotFoundException {
		return ResponseEntity.ok(es.deleteTicketType(eventId, ttId));
	}

	// Tickettype muokkaus, (String)ticketTypeDescription pakollinen, (float)price
	// PAKOLLINEN tai muuten price muuttuu nollaksi, sekä se ei saa olla
	// negatiivinen luku, ADMIN
	@PutMapping("/{id}/tickettypes/{ttid}")
	public ResponseEntity<TicketType> ticketTypeUpdateSingleRest(@PathVariable(name = "id") Long eventId,
			@PathVariable(name = "ttid") Long ttId,
			@RequestBody TicketType newType)
			throws ResourceNotFoundException, ValidationException {
		return ResponseEntity.ok(es.updateTicketType(eventId, ttId, newType));
	}

	// Yksittäisen tapahtuman kuittien listaus, ADMIN, SALES
	@GetMapping("/{id}/salesevents")
	public ResponseEntity<List<SalesEvent>> getSalesEventsByEvent(@PathVariable(name = "id") Long eventId) {
		return ResponseEntity.ok(es.getSalesEvents(eventId));
	}

}
