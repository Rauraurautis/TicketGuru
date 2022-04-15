package fi.triforce.TicketGuru.ticket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import fi.triforce.TicketGuru.Web.service.TicketService;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketServiceTest {
    
    @Autowired
    TicketService ts;

    

    @Test
    void gettingTicketsByEventIdReturnsTickets() {

    }
}
