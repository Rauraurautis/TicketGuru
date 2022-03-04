package fi.triforce.TicketGuru.Domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SalesObject {

	private Long eventId;
	private Long ticketTypeId;
	private int nrOfTickets;
	private int nrOfDiscounted;
	private int discountPercentage;
	
	

}
