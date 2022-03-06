package fi.triforce.TicketGuru.Domain;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Ei tarkoitus olla entity
@NoArgsConstructor
@Getter
@Setter
public class SalesObject {

	private Long eventId;
	private Long ticketTypeId;
	private int nrOfTickets;
	private int nrOfDiscounted;
	private float discountPercentage;
	
	private int discountTicketsLeft;
	
	public SalesObject(Long eventId, Long ticketTypeId, int nrOfTickets, int nrOfDiscounted, float discountPercentage) {
		super();
		this.eventId = eventId;
		this.ticketTypeId = ticketTypeId;
		this.nrOfTickets = nrOfTickets;
		this.nrOfDiscounted = nrOfDiscounted;
		this.discountPercentage = discountPercentage;
	}
	
	public void generateTickets(TicketType tt, SalesEvent newSale, TicketRepository tr)
	{
		discountTicketsLeft = this.nrOfDiscounted;

		for (int i = 0; i < this.getNrOfTickets(); i++)
		{
			Ticket ticket = new Ticket();
			ticket.setTicketSale(newSale);
			ticket.setTicketType(tt);
			ticket.setTicketUsed(false);
			if (discountTicketsLeft > 0) {
				discountTicketsLeft--;
				ticket.setFinalPrice(tt.getPrice() * (1 - this.getDiscountPercentage()));
			} else {
				ticket.setFinalPrice(tt.getPrice());
			}
			tr.save(ticket);				
		}
	}
	
	

}
