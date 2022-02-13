package fi.triforce.TicketGuru.Domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketSaleRepository extends JpaRepository<Long, TicketSale>{
    
}
