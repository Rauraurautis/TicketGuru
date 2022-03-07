package fi.triforce.TicketGuru.Domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
	
	List<Ticket> findByTicketType(TicketType ticketType);
	
	Ticket findByTicketCode(String ticketCode);
}