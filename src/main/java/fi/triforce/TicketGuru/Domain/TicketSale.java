package fi.triforce.TicketGuru.Domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class TicketSale {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long ticketSaleId;
    @OneToMany(mappedBy = "ticketSale")
    private List<Ticket> tickets;
    @ManyToOne
    @JoinColumn(name="salesevent_id", nullable=false)
    private SalesEvent salesEvent;


}
