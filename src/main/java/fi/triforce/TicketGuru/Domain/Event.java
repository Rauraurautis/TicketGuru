package fi.triforce.TicketGuru.Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.time.*;


@Entity
public class Event {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long eventID;
	private String eventDescription;
	private long venueId;
	private long numberOfTickets;
	private LocalDate date;
	
	public Event(String eventDescription, long venueId, long numberOfTickets, LocalDate date) {
		super();
		this.eventDescription = eventDescription;
		this.venueId = venueId;
		this.numberOfTickets = numberOfTickets;
		this.date = date;
	}

	public long getEventID() {
		return eventID;
	}

	public void setEventID(long eventID) {
		this.eventID = eventID;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public long getVenueId() {
		return venueId;
	}

	public void setVenueId(long venueId) {
		this.venueId = venueId;
	}

	public long getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(long numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Event [eventID=" + eventID + ", eventDescription=" + eventDescription + ", venueId=" + venueId
				+ ", numberOfTickets=" + numberOfTickets + ", date=" + date + "]";
	}
	
	
	

}
