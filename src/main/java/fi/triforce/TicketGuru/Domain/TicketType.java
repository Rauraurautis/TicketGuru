package fi.triforce.TicketGuru.Domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.PositiveOrZero;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class TicketType {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long ticketTypeId;
	@NotBlank
	private String ticketTypeDescription;
	@PositiveOrZero
	//@Valid
	//@NotNull //Float defaulttaa aina numeroks, joten notnull on turha tässä tai vastaavissa skenaarioissa.
	private BigDecimal price;
	@ManyToOne
	@JoinColumn(name="eventId")
	@JsonIgnoreProperties({"ticketTypes", "eventVenue"})//Pysäyttää infinite loopin jsonissa 
	private Event event;
	@OneToMany(mappedBy="ticketType")
	@JsonIgnore
	private List<Ticket> tickets;
	
	

	public TicketType(@NotBlank String ticketTypeDescription, @PositiveOrZero BigDecimal price) {
		this.ticketTypeDescription = ticketTypeDescription;
		this.price = price;
	}



	@Override
	public String toString() {
		return "TicketType [ticketTypeID=" + ticketTypeId + ", eventId=" + ", ticketTypeDescription="
				+ ticketTypeDescription + ", price=" + price + "]";
	}
	
	
	
	
	
}