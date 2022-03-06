package fi.triforce.TicketGuru.Domain;

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
	
	public SalesObject(Long eventId, Long ticketTypeId, int nrOfTickets, int nrOfDiscounted, float discountPercentage) {
		super();
		this.eventId = eventId;
		this.ticketTypeId = ticketTypeId;
		this.nrOfTickets = nrOfTickets;
		this.nrOfDiscounted = nrOfDiscounted;
		this.discountPercentage = discountPercentage;
	}
	
	
	
	

}
