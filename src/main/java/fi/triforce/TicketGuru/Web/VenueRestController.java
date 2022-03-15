package fi.triforce.TicketGuru.Web;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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
import fi.triforce.TicketGuru.Domain.VenueRepository;
import fi.triforce.TicketGuru.exception.ResourceNotFoundException;
import fi.triforce.TicketGuru.exception.ValidationException;
import fi.triforce.TicketGuru.utils.ReturnMsg;

@RestController
@RequestMapping("/api/venues")
public class VenueRestController {

	@Autowired
	private VenueRepository vr;

	@Autowired
	private Validator validator;

	@GetMapping
	public List<Venue> venueListRest() {
		return (List<Venue>) vr.findAll();
	}

    @GetMapping("/{id}")
	public ResponseEntity<Venue> venueGetSingleRest(@PathVariable(name = "id") Long id)
			throws ResourceNotFoundException {
		Venue venue = vr.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find a venue with the id " + id));
		return ResponseEntity.ok(venue);
	}

    @PostMapping
	public ResponseEntity<Venue> venuePostRest(@RequestBody Venue venue) throws ValidationException
    {
    	Set<ConstraintViolation<Venue>> result = validator.validate(venue);
		if (!result.isEmpty()) {
			String errorMsg = result.toString();
			String[] splitMsg = errorMsg.split("=");
			String propertyName = splitMsg[2].split(",")[0] + " " + splitMsg[1].split(",")[0].replace("'", "");
			throw new ValidationException(propertyName);
		}
    	return new ResponseEntity<Venue>(vr.save(venue), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> venueDeleteSingleRest(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		Venue venue = vr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find a venue with the id " + id));
		vr.delete(venue);
		return ResponseEntity.ok(new ReturnMsg("Deleted a venue with the id " + id).getReturnMsg());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Venue> venueUpdateSingleRest(@PathVariable(name = "id") Long id, @RequestBody Venue editedVenue)
		throws ResourceNotFoundException, ValidationException {
		Venue venue = vr.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find a venue with the id " + id));
		Set<ConstraintViolation<Venue>> result = validator.validate(editedVenue);
		if (!result.isEmpty()) {
			String errorMsg = result.toString();
			String[] splitMsg = errorMsg.split("=");
			String propertyName = splitMsg[2].split(",")[0] + " " + splitMsg[1].split(",")[0].replace("'", "");
			throw new ValidationException(propertyName);
		}
		venue.setVenueName(editedVenue.getVenueName());
		venue.setVenueAddress(editedVenue.getVenueAddress());
		venue.setVenueCity(editedVenue.getVenueCity());
		return ResponseEntity.ok(vr.save(venue));
	}

}
