package fi.triforce.TicketGuru.ticket;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Validator;

import fi.triforce.TicketGuru.Domain.EventRepository;
import fi.triforce.TicketGuru.Domain.SalesEventRepository;
import fi.triforce.TicketGuru.Domain.Ticket;
import fi.triforce.TicketGuru.Domain.TicketRepository;
import fi.triforce.TicketGuru.Domain.TicketType;
import fi.triforce.TicketGuru.Domain.TicketTypeRepository;
import fi.triforce.TicketGuru.Web.service.SalesService;
import fi.triforce.TicketGuru.Web.service.TicketService;
import fi.triforce.TicketGuru.dto.SalesObject;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketServiceTest {

    private TicketService ts;
    @Autowired
    private EventRepository er;
    @Autowired
    private SalesEventRepository ser;
    @Autowired
    private TicketTypeRepository ttr;
    @Autowired
    private TicketRepository tr;
    @Autowired
    private Validator validator;

    private SalesService ss;

    @BeforeAll
    void setUp() {
        ss = new SalesService(ttr, ser, tr, validator, 0);
        ts = new TicketService(er, ttr, tr);

        TicketType ticketType = new TicketType("Aikuinen", BigDecimal.valueOf(10));
        ticketType = ttr.save(ticketType);

        SalesObject salesObject = new SalesObject((long) 1, 5, 0, BigDecimal.valueOf(0));
        SalesObject salesObjectTwo = new SalesObject((long) 1, 3, 0, BigDecimal.valueOf(0));
        List<SalesObject> list = List.of(salesObject, salesObjectTwo);

        ss.createSale(list);
    }

    @Test
    void gettingTicketsByEventIdReturnsTickets() {
        List<Ticket> tickets = ts.getTicketsByEventId(Long.valueOf(1));

        assertThat(tickets.size()).isEqualTo(8);

    }

    @Test
    void gettingSingleTicketByTicketCodeReturnsTicket() {
        Ticket ticket = tr.findById((long) 1).get();
        String code = ticket.getTicketCode();

        assertThat(ts.singleTicketByTicketCode(code).getTicketId()).isEqualTo(ticket.getTicketId());
    }

    @Test
    void settingTicketUsedSetsItUsed() {
        Ticket ticket = tr.findById((long) 1).get();
        assertThat(ts.setTicketUsed(ticket).getTicketUsed()).isTrue();
    }

}