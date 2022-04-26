package fi.triforce.TicketGuru.Domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Validated
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventId;
	// @Column()
	@NotBlank
	private String eventTitle;
	private String eventDescription;
	// @Column()
	@PositiveOrZero
	private Long numberOfTickets;
	// @Column()
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", shape = JsonFormat.Shape.STRING)
	private LocalDateTime dateOfEvent;
	@ManyToOne
	@JoinColumn(name = "venueId")
	private Venue eventVenue;
	@OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("event") // Pysäyttää infinite loopin jsonissa
	private List<TicketType> ticketTypes;

	public Event(@NotBlank String eventTitle, @PositiveOrZero Long numberOfTickets) {
		this.eventTitle = eventTitle;
		this.numberOfTickets = numberOfTickets;
	}

	@Override
	public String toString() {
		return "Event [eventID=" + eventId + ", eventTitle=" + eventTitle + ", eventDescription=" + eventDescription
				+ ", venueId="
				+ ", numberOfTickets=" + numberOfTickets + ", date=" + dateOfEvent + "]";
	}

}