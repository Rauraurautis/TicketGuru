package fi.triforce.TicketGuru.Domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;
    private LocalDate dateOfSale;
    @OneToMany(mappedBy = "ticketSale")
    private List<Ticket> tickets;

    public LocalDate getDateOfSale() {
        return this.dateOfSale;
    }

    public void setDateOfSale(LocalDate newDate) {
        this.dateOfSale = newDate;
    }
}