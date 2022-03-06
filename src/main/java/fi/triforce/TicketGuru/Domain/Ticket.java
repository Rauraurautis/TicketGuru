package fi.triforce.TicketGuru.Domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ticket {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long ticketId;
	private String ticketCode;
	private boolean ticketUsed;
	@ManyToOne
	@JoinColumn(name = "salesEvent_id")
	@JsonIgnore
	private SalesEvent ticketSale;
	@ManyToOne
	@JoinColumn(name = "ticketType_id")
	/*@JsonIgnoreProperties("event")*/
	private TicketType ticketType;
	private float finalPrice;

	@Override
	public String toString() {
		return "Ticket [ticketID=" + ticketId + ", ticketCode=" + ticketCode
				+ ", ticketUsed=" + ticketUsed + "]";
	}
	
	public void generateTicketCode() {
		this.ticketCode = String.valueOf(UUID.randomUUID());
	}

}
