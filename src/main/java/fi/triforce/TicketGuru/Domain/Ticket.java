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
	@JsonIgnoreProperties({"price", "ticketTypeId"})
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (finalPrice == null) {
			if (other.finalPrice != null)
				return false;
		} else if (!finalPrice.equals(other.finalPrice))
			return false;
		if (ticketCode == null) {
			if (other.ticketCode != null)
				return false;
		} else if (!ticketCode.equals(other.ticketCode))
			return false;
		if (ticketId != other.ticketId)
			return false;
		if (ticketSale == null) {
			if (other.ticketSale != null)
				return false;
		} else if (!ticketSale.equals(other.ticketSale))
			return false;
		if (ticketType == null) {
			if (other.ticketType != null)
				return false;
		} else if (!ticketType.equals(other.ticketType))
			return false;
		if (ticketUsed == null) {
			if (other.ticketUsed != null)
				return false;
		} else if (!ticketUsed.equals(other.ticketUsed))
			return false;
		return true;
	}

}
