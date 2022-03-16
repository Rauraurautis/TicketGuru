package fi.triforce.TicketGuru.Web.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.triforce.TicketGuru.Domain.EventRepository;
import fi.triforce.TicketGuru.Domain.Ticket;
import fi.triforce.TicketGuru.Domain.TicketRepository;
import fi.triforce.TicketGuru.Domain.TicketType;
import fi.triforce.TicketGuru.Domain.TicketTypeRepository;
import fi.triforce.TicketGuru.exception.ResourceNotFoundException;
import fi.triforce.TicketGuru.utils.ReturnMsg;

@RestController
@RequestMapping("/api/events/")
public class TicketsRestController {

	@Autowired
	private EventRepository er;
	@Autowired
	private TicketTypeRepository ttr;
	@Autowired
	private TicketRepository tr;

	// Listaa kaikki tapahtuman liput
	@GetMapping("{eventid}/tickets")
	public List<Ticket> ticketsListRest(@PathVariable(name = "eventid") Long eventId) {
		er.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + eventId));
		return listTicketsforEvent(eventId);
	}

	// Yksittäisen lipun tiedot
	@GetMapping("{eventid}/tickets/{ticketid}")
	public ResponseEntity<Ticket> singleTicketRest(@PathVariable(name = "eventid") Long eventId,
			@PathVariable(name = "ticketid") Long ticketId) {
		er.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + eventId));
		Ticket ticket = tr.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find a ticket with the id " + ticketId));
		return ResponseEntity.ok(ticket);
	}

	// Yksittäisen lipun merkkaus käytetyksi
	@PutMapping("{eventid}/tickets")
	public ResponseEntity<?> setTicketUsed(@RequestBody Ticket t) {
		String ticketCode = t.getTicketCode();
		Ticket ticket = tr.findByTicketCode(ticketCode);
		if (ticket == null) {
			return new ResponseEntity<>(new ReturnMsg("No ticket found with the code " + ticketCode).getReturnMsg(), HttpStatus.BAD_REQUEST);
		} else if (ticket.getTicketUsed()) {
			return new ResponseEntity<>(new ReturnMsg("The ticket with the ticketcode " + ticketCode + " has already been used").getReturnMsg(), HttpStatus.BAD_REQUEST);
		}
		ticket.setTicketUsed(true);
		return ResponseEntity.ok(tr.save(ticket));
	}

	// Inserted eventid must be confirmed
	private List<Ticket> listTicketsforEvent(Long eventId) {
		List<TicketType> types = ttr.findByEvent(er.findById(eventId).get());
		List<Ticket> tickets = new ArrayList<Ticket>();
		for (int i = 0; i < types.size(); i++) {
			tickets.addAll(tr.findByTicketType(types.get(i)));
		}
		return tickets;
	}

}
