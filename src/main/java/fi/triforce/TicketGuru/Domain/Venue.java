package fi.triforce.TicketGuru.Domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

/*public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long venueId;
    private String venueName;
    private String venueAddress;
    private String venueCity;
    @OneToMany(mappedBy = "eventVenue")
    @JsonIgnore
    private List<Event> events;
    
    @Override
    public String toString() {
        return "Venue [venueAddress=" + venueAddress + ", venueCity=" + venueCity + ", venueId=" + venueId
                + ", venueName=" + venueName + "]";
    }

}*/

public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long venueId;
    //@Column()
    private String venueName;
    //@Column()
    private String venueAddress;
    //@Column()
    private String venueCity;
    @OneToMany(mappedBy="venue")
	private List<Event> events;
    @Override
    public String toString() {
        return "Venue [venueAddress=" + venueAddress + ", venueCity=" + venueCity + ", venueId=" + venueId
                + ", venueName=" + venueName + "]";
    }
}