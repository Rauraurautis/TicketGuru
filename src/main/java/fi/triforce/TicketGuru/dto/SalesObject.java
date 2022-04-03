package fi.triforce.TicketGuru.dto;


import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Ei tarkoitus olla entity
@NoArgsConstructor
@Getter
@Setter
@Validated
public class SalesObject {
	@NotNull
	private Long ticketTypeId;
	@NotNull
	@Min(1)
	private int nrOfTickets;
	@Min(0)
	@Max(10)
	private int nrOfDiscounted;
	@Min(0)
	@Max(1)
	private BigDecimal discountPercentage;
	
	private int discountTicketsLeft;
	
	public SalesObject(Long ticketTypeId, int nrOfTickets, int nrOfDiscounted, BigDecimal discountPercentage) {
		super();
		this.ticketTypeId = ticketTypeId;
		this.nrOfTickets = nrOfTickets;
		this.nrOfDiscounted = nrOfDiscounted;
		this.discountPercentage = discountPercentage;
	}

}
