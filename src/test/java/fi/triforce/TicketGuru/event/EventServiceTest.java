package fi.triforce.TicketGuru.event;

import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fi.triforce.TicketGuru.Domain.Event;
import fi.triforce.TicketGuru.Domain.EventRepository;
import fi.triforce.TicketGuru.Domain.SalesEventRepository;
import fi.triforce.TicketGuru.Domain.TicketType;
import fi.triforce.TicketGuru.Domain.TicketTypeRepository;
import fi.triforce.TicketGuru.Domain.VenueRepository;
import fi.triforce.TicketGuru.Web.service.EventService;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventServiceTest {

    @Autowired
    private EventRepository er;

    @Autowired
    private VenueRepository vr;

    @Autowired
    private TicketTypeRepository ttr;

    @Autowired
    private SalesEventRepository sr;

    @Autowired
    private Validator validator;

    private EventService es;

    @BeforeAll
    void setUp() {
        es = new EventService(er, vr, ttr, sr, validator);
    }

    @Test
    void addingEventAddsEvent() {
        Event event = new Event("Testtitle", Long.valueOf(100));
        assertThat(es.addEvent(event)).isEqualTo(event);
    }

    @Test
    void updatingEventUpdatesEvent() {
        Event event = er.findById(Long.valueOf(1)).get();
        Event changedEvent = event;
        changedEvent.setEventTitle("Changed title");
        assertThat(es.updateEvent(Long.valueOf(1), changedEvent).getEventTitle().equals(changedEvent.getEventTitle()))
                .isTrue();
    }

    @Test
    void addingTicketTypeAddsTicketType() {
        Event event = er.findById(Long.valueOf(1)).get();
        TicketType ticketType = new TicketType("Test type", BigDecimal.valueOf(10));

        assertThat(es.addTicketType(event.getEventId(), ticketType)).isEqualTo(ticketType);
    }

    @Test
    void updatingTicketTypeUpdatesTicketType() {
        Event event = er.findById(Long.valueOf(1)).get();
        List<TicketType> ticketTypes = ttr.findByEvent(event);
        TicketType changedTicketType = ticketTypes.get(1);
        changedTicketType.setTicketTypeDescription("Changed description");
        assertThat(es.updateTicketType(event.getEventId(), changedTicketType.getTicketTypeId(),
                changedTicketType).getTicketTypeDescription().equals(changedTicketType.getTicketTypeDescription()));
    }
}