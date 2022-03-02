package fi.triforce.TicketGuru.Domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TicketTypeRepository extends CrudRepository<TicketType, Long> {
	
	List<TicketType> findByEvent(Event event);
}
