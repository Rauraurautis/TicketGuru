package fi.triforce.TicketGuru.Web.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fi.triforce.TicketGuru.Domain.Ticket;
import fi.triforce.TicketGuru.Web.service.TicketService;
import fi.triforce.TicketGuru.exception.NotFoundException;
import fi.triforce.TicketGuru.exception.TicketUsedException;


@RestController
@RequestMapping("/api/events/")
public class TicketsRestController {

	@Autowired
	private TicketService ts;

	// Listaa kaikki tapahtuman liput
	@GetMapping("{eventid}/tickets")
	public ResponseEntity<List<Ticket>> ticketsListRest(@PathVariable(name = "eventid") Long eventId) {
		return ResponseEntity.ok(ts.getTicketsByEventId(eventId));
	}

	// Yksittäisen lipun tiedot
	@GetMapping("{eventid}/tickets/{ticketid}")
	public ResponseEntity<Ticket> singleTicketRest(@PathVariable(name = "eventid") Long eventId,
			@PathVariable(name = "ticketid") Long ticketId) {
		return ResponseEntity.ok(ts.singleTicketByEvent(eventId, ticketId));
	}

	// Yksittäisen lipun merkkaus käytetyksi
	@PutMapping("{eventid}/tickets")
	public ResponseEntity<?> setTicketUsed(@RequestBody Ticket t) throws NotFoundException, TicketUsedException {
		return ResponseEntity.ok(ts.setTicketUsed(t));
	}

}
