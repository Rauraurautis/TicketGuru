package fi.triforce.TicketGuru.Domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SalesEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesEventId;
    @ManyToMany(mappedBy = "salesEvents")
    @JsonIgnoreProperties("event")
    private List<Event> events;
    private LocalDateTime dateOfSale;
    @OneToMany(mappedBy = "ticketSale")
    private List<Ticket> tickets;

    public LocalDateTime getDateOfSale() {
        return this.dateOfSale;
    }

    public void setDateOfSale(LocalDateTime newDate) {
        this.dateOfSale = newDate;
    }
    
    public void addTicket(Ticket ticket) {
    	if (this.tickets == null) {
    		this.tickets = new ArrayList<>();
    	}
    	tickets.add(ticket);
    }
}