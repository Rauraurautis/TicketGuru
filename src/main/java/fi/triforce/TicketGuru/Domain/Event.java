package fi.triforce.TicketGuru.Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.*;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Event {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long eventID;
	private String eventDescription;
	private Long numberOfTickets;
	private Long venueId;
	private LocalDate date;
	@ManyToOne
	@JoinTable(name = "venue_id")
	private Venue eventVenue;
	@OneToMany(mappedBy="event")
	private List<TicketType> ticketTypes;

	@Override
	public String toString() {
		return "Event [eventID=" + eventID + ", eventDescription=" + eventDescription + ", venueId="
				+ ", numberOfTickets=" + numberOfTickets + ", date=" + date + "]";
	}
	
	
	

}
