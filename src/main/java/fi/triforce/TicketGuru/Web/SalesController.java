package fi.triforce.TicketGuru.Web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.triforce.TicketGuru.Domain.EventRepository;
import fi.triforce.TicketGuru.Domain.SalesEventRepository;
import fi.triforce.TicketGuru.Domain.SalesObject;
import fi.triforce.TicketGuru.Domain.Ticket;
import fi.triforce.TicketGuru.Domain.TicketRepository;
import fi.triforce.TicketGuru.Domain.TicketType;
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
	
	private int discountTicketsLeft;
	
	@PostMapping
	public ResponseEntity<?> makeASaleRest(@RequestBody List<SalesObject> sale) {
		System.out.println(sale);
		SalesEvent receipt = createTicketsFromSalesObjects(sale);
		return ResponseEntity.ok(receipt);
	}
	
	//Purkaa rivit lippuentityiksi, liittää saleseventtiin yms. Osan toiminnoista voisi ehkä siirtää SalesObjectin metodeiksi.
	private SalesEvent createTicketsFromSalesObjects(List<SalesObject> sale) throws ResourceNotFoundException {
		SalesEvent newSale = sr.save(new SalesEvent());
		for(int i=0; i < sale.size(); i++) {
			int index = i;
			er.findById(sale.get(i).getEventId())
					.orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + sale.get(index).getEventId()));		
			TicketType tt = ttr.findById(sale.get(i).getTicketTypeId())
					.orElseThrow(() -> new ResourceNotFoundException("Cannot find a tickettype with the id " + sale.get(index).getTicketTypeId()));
			
			discountTicketsLeft = sale.get(i).getNrOfDiscounted();

			for (int o = 0; o < sale.get(i).getNrOfTickets(); o++)
			{
				Ticket ticket = new Ticket();
				ticket.setTicketSale(newSale);
				ticket.setTicketType(tt);
				ticket.setTicketUsed(false);
				if (discountTicketsLeft > 0) {
					discountTicketsLeft--;
					ticket.setFinalPrice(tt.getPrice() * (1 - sale.get(i).getDiscountPercentage()));
				} else {
					ticket.setFinalPrice(tt.getPrice());
				}
				ticket.generateTicketCode();
				newSale.addTicket(ticket);
				tr.save(ticket);				
			}

		}
		newSale.setDateOfSale(LocalDateTime.now());
		return newSale;
	}
	
}
