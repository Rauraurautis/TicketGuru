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
import fi.triforce.TicketGuru.Domain.Venue;
import fi.triforce.TicketGuru.Web.service.VenueService;
import fi.triforce.TicketGuru.exception.ResourceNotFoundException;
import fi.triforce.TicketGuru.exception.ValidationException;

@RestController
@RequestMapping("/api/venues")
public class VenueRestController {

	@Autowired
	private VenueService vs;

	@GetMapping
	public ResponseEntity<List<Venue>> venueListRest() {
		return ResponseEntity.ok(vs.getAllVenues());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Venue> venueGetSingleRest(@PathVariable(name = "id") Long venueId)
			throws ResourceNotFoundException {
		return ResponseEntity.ok(vs.getSingleVenue(venueId));
	}

	@PostMapping
	public ResponseEntity<Venue> venuePostRest(@RequestBody Venue venue) throws ValidationException {
		return new ResponseEntity<Venue>(vs.postVenue(venue), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> venueDeleteSingleRest(@PathVariable(name = "id") Long venueId)
			throws ResourceNotFoundException {
		return ResponseEntity.ok(vs.deleteVenue(venueId));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Venue> venueUpdateSingleRest(@PathVariable(name = "id") Long venueId,
			@RequestBody Venue editedVenue)
			throws ResourceNotFoundException, ValidationException {
		return ResponseEntity.ok(vs.updateVenue(venueId, editedVenue));
	}

}
