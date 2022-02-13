package fi.triforce.TicketGuru.Domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class TicketSale {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long ticketSaleId;
    @OneToOne(mappedBy = "ticketSale")
    private Ticket ticket;
    @ManyToOne
    @JoinColumn(name="salesevent_id", nullable=false)
    private SalesEvent salesEvent;


}
