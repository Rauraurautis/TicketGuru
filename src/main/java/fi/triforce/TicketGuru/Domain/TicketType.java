package fi.triforce.TicketGuru.Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class TicketType {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long ticketTypeID;
	private long eventId;
	private String ticketTypeDescription;
	private float price;
	
	public TicketType(long ticketTypeID, long eventId, String ticketTypeDescription, float price) {
		super();
		this.ticketTypeID = ticketTypeID;
		this.eventId = eventId;
		this.ticketTypeDescription = ticketTypeDescription;
		this.price = price;
	}

	public long getTicketTypeID() {
		return ticketTypeID;
	}

	public void setTicketTypeID(long ticketTypeID) {
		this.ticketTypeID = ticketTypeID;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getTicketTypeDescription() {
		return ticketTypeDescription;
	}

	public void setTicketTypeDescription(String ticketTypeDescription) {
		this.ticketTypeDescription = ticketTypeDescription;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "TicketType [ticketTypeID=" + ticketTypeID + ", eventId=" + eventId + ", ticketTypeDescription="
				+ ticketTypeDescription + ", price=" + price + "]";
	}
	
	
	
	
	
}