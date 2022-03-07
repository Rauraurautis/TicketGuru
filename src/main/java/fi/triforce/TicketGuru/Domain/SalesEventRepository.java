package fi.triforce.TicketGuru.Domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SalesEventRepository extends JpaRepository<SalesEvent, Long> {
    
    @Query("FROM SalesEvent se JOIN se.events see where see.eventId = ?1")
    List<SalesEvent> getAllSalesEventsByEventId(Long id);
    
    //Ilman many-to-many-suhdetta.
    @Query(value = "select distinct se from SalesEvent se join se.tickets t join t.ticketType tt join tt.event e where e.eventId=?1")
    List<SalesEvent> getAllSalesEventsByEvent(Long id);
    

}
