package fi.triforce.TicketGuru.Domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dateOfSale;
    @OneToMany(mappedBy = "ticketSale")
    @JsonIgnoreProperties("ticketUsed")
    private List<Ticket> tickets = new ArrayList<Ticket>();

    public LocalDateTime getDateOfSale() {
        return this.dateOfSale;
    }

    public void setDateOfSale(LocalDateTime newDate) {
        this.dateOfSale = newDate;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }
}