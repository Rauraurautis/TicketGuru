package fi.triforce.TicketGuru.Domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long ticketID;
	private long ticketTypeID;
	private int ticketCode;
	private boolean ticketUsed;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticketsale_id", referencedColumnName="ticketSaleId")
	private TicketSale ticketSale;

	
	
	public Ticket() {}	

	public Ticket(long ticketTypeID, int ticketCode, boolean ticketUsed) {
		super();
		this.ticketTypeID = ticketTypeID;
		this.ticketCode = ticketCode;
		this.ticketUsed = ticketUsed;
	}

	public long getTicketTypeID() {
		return ticketTypeID;
	}

	public void setTicketTypeID(long ticketTypeID) {
		this.ticketTypeID = ticketTypeID;
	}

	public int getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(int ticketCode) {
		this.ticketCode = ticketCode;
	}

	public boolean isTicketUsed() {
		return ticketUsed;
	}

	public void setTicketUsed(boolean ticketUsed) {
		this.ticketUsed = ticketUsed;
	}

	@Override
	public String toString() {
		return "Ticket [ticketID=" + ticketID + ", ticketTypeID=" + ticketTypeID + ", ticketCode=" + ticketCode
				+ ", ticketUsed=" + ticketUsed + "]";
	}
	
	

}
