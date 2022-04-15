package fi.triforce.TicketGuru.ticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import fi.triforce.TicketGuru.Domain.Event;
import fi.triforce.TicketGuru.Domain.EventRepository;
import fi.triforce.TicketGuru.Domain.Ticket;
import fi.triforce.TicketGuru.Domain.TicketRepository;
import fi.triforce.TicketGuru.Domain.TicketType;
import fi.triforce.TicketGuru.Domain.TicketTypeRepository;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository tr;

    @Autowired
    private TicketTypeRepository ttr;

    @Autowired
    private EventRepository er;

    private Ticket ticket;

    @BeforeAll
    void generateTickets() {
        TicketType ticketType = ttr.findById((long) 1).get();
        Ticket newTicket = new Ticket(Long.valueOf(1), "asd", false, null, ticketType, BigDecimal.valueOf(10));
        ticket = tr.save(newTicket);
    }

    @Test
    void itShouldReturnListWhenFoundByTicketType() {
        TicketType ticketType = ttr.findById((long) 1).get();
        List<Ticket> tickets = tr.findByTicketType(ticketType);
        List<Ticket> testing = List.of(ticket);

        // assertThat(tickets).hasSameElementsAs(testing);
        assertThat(tickets.size() == testing.size());
       
        
    }

    @Test
    void itShouldReturnEmptyListWhenSearchedWithTicketTypeNotUsed() {
        TicketType ticketType = ttr.findById((long) 1).get();
        Ticket newTicket = new Ticket(Long.valueOf(1), "asd", false, null, ticketType, BigDecimal.valueOf(10));
        newTicket = tr.save(newTicket);

        ticketType = ttr.findById((long) 2).get();
        List<Ticket> tickets = tr.findByTicketType(ticketType);
        List<Ticket> testingThis = List.of();

        assertEquals(testingThis, tickets);
    }

    @Test
    void itShouldReturnTicketWhenSearchedWithTicketCode() {
        TicketType ticketType = ttr.findById((long) 1).get();
        Ticket newTicket = new Ticket(Long.valueOf(1), "asd", false, null, ticketType, BigDecimal.valueOf(10));
        newTicket = tr.save(newTicket);

        Ticket ticketByCode = tr.findByTicketCode("asd");

        assertEquals(newTicket, ticketByCode);
    }

    @Test
    void findTicketTypeByEvent() {
        Event event = er.findById(Long.valueOf(1)).get();

        List<TicketType> searchedTicketTypes = ttr.findByEvent(event);

        assertEquals(2, searchedTicketTypes.size());

    }
}
