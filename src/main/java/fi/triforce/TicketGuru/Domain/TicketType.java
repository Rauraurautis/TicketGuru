package fi.triforce.TicketGuru.Domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketType {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long ticketTypeID;
	private String ticketTypeDescription;
	private float price;
	@ManyToOne
	@JoinColumn(name="eventId")
	@JsonIgnoreProperties("ticketTypes")//Pysäyttää infinite loopin jsonissa 
	private Event event;
	@OneToMany(mappedBy="ticketType")
	@JsonIgnore
	private List<Ticket> tickets;
	
	@Override
	public String toString() {
		return "TicketType [ticketTypeID=" + ticketTypeID + ", eventId=" + ", ticketTypeDescription="
				+ ticketTypeDescription + ", price=" + price + "]";
	}
	
	
	
	
	
}