package fi.triforce.TicketGuru.Web;

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

import fi.triforce.TicketGuru.Domain.Venue;
import fi.triforce.TicketGuru.Domain.VenueRepository;
import fi.triforce.TicketGuru.utils.ReturnMsg;

@RestController
@RequestMapping("/api/venues")
public class VenueRestController {

	@Autowired
	private VenueRepository vr;


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
	public ResponseEntity<Venue> venuePostRest(@RequestBody Venue venue) {
		return ResponseEntity.ok(vr.save(venue));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> venueDeleteSingleRest(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		Venue venue = vr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find a venue with the id " + id));
		vr.delete(venue);
		return ResponseEntity.ok(new ReturnMsg("Deleted a venue with the id " + id).getReturnMsg());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Venue> venueUpdateSingleRest(@PathVariable(name = "id") Long id, @Valid @RequestBody Venue editedVenue)
		throws ResourceNotFoundException {
		Venue venue = vr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find a venue with the id " + id));
		venue.setVenueName(editedVenue.getVenueName());
		venue.setVenueAddress(editedVenue.getVenueAddress());
		venue.setVenueCity(editedVenue.getVenueCity());
		return ResponseEntity.ok(vr.save(venue));
	}

}
