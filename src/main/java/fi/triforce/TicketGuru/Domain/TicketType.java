package fi.triforce.TicketGuru.Domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketType {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long ticketTypeID;
	private long eventId;
	private String ticketTypeDescription;
	private float price;
	@ManyToOne
	@JoinColumn(name="event_id")
	private Event event;
	@OneToMany(mappedBy="ticketType")
	private List<Ticket> tickets;
	
	@Override
	public String toString() {
		return "TicketType [ticketTypeID=" + ticketTypeID + ", eventId=" + eventId + ", ticketTypeDescription="
				+ ticketTypeDescription + ", price=" + price + "]";
	}
	
	
	
	
	
}