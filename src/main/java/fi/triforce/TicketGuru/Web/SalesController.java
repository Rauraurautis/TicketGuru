package fi.triforce.TicketGuru.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.triforce.TicketGuru.Domain.EventRepository;
import fi.triforce.TicketGuru.Domain.SalesEventRepository;
import fi.triforce.TicketGuru.Domain.SalesObject;
import fi.triforce.TicketGuru.Domain.TicketRepository;
import fi.triforce.TicketGuru.Domain.TicketTypeRepository;
import fi.triforce.TicketGuru.Domain.SalesEvent;

@RestController
@RequestMapping("/api/sales")
public class SalesController {
	
	@Autowired
	private EventRepository er;
	
	@Autowired
	private TicketTypeRepository ttr;
	
	@Autowired
	private SalesEventRepository sr;
	
	@Autowired
	private TicketRepository tr;
	
	@PostMapping
	public ResponseEntity<?> makeASaleRest(@RequestBody SalesObject sale) {
		SalesEvent receipt = new SalesEvent();
		
		return ResponseEntity.ok(receipt);
	}
	
}
