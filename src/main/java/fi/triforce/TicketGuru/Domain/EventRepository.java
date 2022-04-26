package fi.triforce.TicketGuru.Domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
	
	@Query(value = "select distinct e from Event e where e.dateOfEvent > current_date() order by e.dateOfEvent")
	List<Event> getUpcomingEvents();
	
	@Query(value = "select distinct e from Event e where e.dateOfEvent < current_date() order by e.dateOfEvent")
	List<Event> getPastEvents();
}