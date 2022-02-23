package fi.triforce.TicketGuru.Domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ticket {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long ticketID;
	private long ticketTypeID;
	private int ticketCode;
	private boolean ticketUsed;
	@ManyToOne
	@JoinColumn(name = "salesEvent_id")
	private SalesEvent salesEvent;
	@ManyToOne
	@JoinColumn(name = "ticketType_id")
	private TicketType ticketType;

	@Override
	public String toString() {
		return "Ticket [ticketID=" + ticketID + ", ticketTypeID=" + ticketTypeID + ", ticketCode=" + ticketCode
				+ ", ticketUsed=" + ticketUsed + "]";
	}
	
	

}
