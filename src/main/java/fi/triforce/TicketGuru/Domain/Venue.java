package fi.triforce.TicketGuru.Domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long venueId;
    private String venueName;
    private String venueAddress;
    private String venueCity;
    @OneToMany(mappedBy = "eventVenue")
    private List<Event> events;
    
    @Override
    public String toString() {
        return "Venue [venueAddress=" + venueAddress + ", venueCity=" + venueCity + ", venueId=" + venueId
                + ", venueName=" + venueName + "]";
    }

}
