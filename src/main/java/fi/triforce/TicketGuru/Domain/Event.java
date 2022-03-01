package fi.triforce.TicketGuru.Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	/*@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long eventID;
	private String eventDescription;
	private Long numberOfTickets;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", shape = JsonFormat.Shape.STRING)
	private LocalDateTime date;
	@ManyToOne
	@JoinTable(name = "venue_id")
	private Venue eventVenue;
	@OneToMany(mappedBy="event")
	private List<TicketType> ticketTypes;*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long eventID;
	//@Column()
	private String eventDescription;
	//@Column()
	private Long numberOfTickets;
	//@Column()
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", shape = JsonFormat.Shape.STRING)
	private LocalDateTime date;
	@ManyToOne
	@JoinColumn(name="venueId")
	private Venue venue;
	@OneToMany(mappedBy="event")
	private List<TicketType> ticketTypes;

	@Override
	public String toString() {
		return "Event [eventID=" + eventID + ", eventDescription=" + eventDescription + ", venueId="
				+ ", numberOfTickets=" + numberOfTickets + ", date=" + date + "]";
	}
	
	
	

}
