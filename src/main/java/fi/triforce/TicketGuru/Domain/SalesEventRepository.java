package fi.triforce.TicketGuru.Domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesEventRepository extends JpaRepository<SalesEvent, Long> {
    
}
