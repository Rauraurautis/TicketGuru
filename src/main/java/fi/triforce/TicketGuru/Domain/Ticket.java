package fi.triforce.TicketGuru.Domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ticketId;
	private String ticketCode;
	private Boolean ticketUsed;
	@ManyToOne
	@JoinColumn(name = "salesEvent_id")
	@JsonIgnore
	private SalesEvent ticketSale;
	@ManyToOne
	@JoinColumn(name = "ticketType_id")
	// @JsonIgnoreProperties("event")
	private TicketType ticketType;
	private BigDecimal finalPrice;

	@Override
	public String toString() {
		return "Ticket [ticketID=" + ticketId + ", ticketCode=" + ticketCode
				+ ", ticketUsed=" + ticketUsed + "]";
	}

	public void generateTicketCode() {
		this.ticketCode = String.valueOf(UUID.randomUUID());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		return false;

	}

}
