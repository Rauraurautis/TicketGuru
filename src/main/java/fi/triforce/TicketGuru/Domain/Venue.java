package fi.triforce.TicketGuru.Domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long venueId;
    //@Column()
    @NotBlank
    private String venueName;
    //@Column()
    @NotBlank
    private String venueAddress;
    //@Column()
    @NotBlank
    private String venueCity;
    @OneToMany(mappedBy="eventVenue")
    @JsonIgnore
	private List<Event> events;
    @Override
    public String toString() {
        return "Venue [venueAddress=" + venueAddress + ", venueCity=" + venueCity + ", venueId=" + venueId
                + ", venueName=" + venueName + "]";
    }
}