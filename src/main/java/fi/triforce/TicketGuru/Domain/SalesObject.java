package fi.triforce.TicketGuru.Domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Ei tarkoitus olla entity
@NoArgsConstructor
@Getter
@Setter
public class SalesObject {

	private Long ticketTypeId;
	private int nrOfTickets;
	private int nrOfDiscounted;
	private float discountPercentage;
	
	private int discountTicketsLeft;
	
	public SalesObject(Long ticketTypeId, int nrOfTickets, int nrOfDiscounted, float discountPercentage) {
		super();
		this.ticketTypeId = ticketTypeId;
		this.nrOfTickets = nrOfTickets;
		this.nrOfDiscounted = nrOfDiscounted;
		this.discountPercentage = discountPercentage;
	}

}
