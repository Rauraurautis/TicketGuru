package fi.triforce.TicketGuru.Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long venueId;
    private String venueName;
    private String venueAddress;
    private String venueCity;
    

    public Venue() {
    }

    public Venue(long venueId, String venueName) {
        this.venueId = venueId;
        this.setVenueName(venueName);
    }

    public Venue(long venueId, String venueName, String venueCity) {
        this.venueId = venueId;
        this.setVenueName(venueName);
        this.setVenueCity(venueCity);
    }

    public Venue(long venueId, String venueName, String venueAddress, String venueCity) {
        this.venueId = venueId;
        this.setVenueName(venueName);
        this.setVenueAddress(venueAddress);
        this.setVenueCity(venueCity);
    }

    public String getVenueCity() {
        return venueCity;
    }

    public void setVenueCity(String venueCity) {
        this.venueCity = venueCity;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    @Override
    public String toString() {
        return "Venue [venueAddress=" + venueAddress + ", venueCity=" + venueCity + ", venueId=" + venueId
                + ", venueName=" + venueName + "]";
    }

}
