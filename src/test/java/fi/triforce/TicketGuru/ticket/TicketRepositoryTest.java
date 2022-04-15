package fi.triforce.TicketGuru.ticket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import fi.triforce.TicketGuru.Domain.Ticket;
import fi.triforce.TicketGuru.Domain.TicketRepository;
import fi.triforce.TicketGuru.Domain.TicketType;
import fi.triforce.TicketGuru.Domain.TicketTypeRepository;

@DataJpaTest
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository tr;

    @Autowired
    private TicketTypeRepository ttr;

    @Test
    void itShouldReturnListWhenFoundByTicketType() {
        TicketType ticketType = new TicketType("Aikuinen", BigDecimal.valueOf(10));
        ticketType = ttr.save(ticketType);

        Ticket newTicket = new Ticket(Long.valueOf(1), "asd", false, null, ticketType, BigDecimal.valueOf(10));
        newTicket = tr.save(newTicket);

        List<Ticket> tickets = tr.findByTicketType(ticketType);
        List<Ticket> testingThis = List.of(newTicket);
        assertEquals(testingThis, tickets);
    }
}
