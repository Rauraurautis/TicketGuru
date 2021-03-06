package fi.triforce.TicketGuru.Web.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import fi.triforce.TicketGuru.Domain.SalesEventRepository;
import fi.triforce.TicketGuru.Domain.Ticket;
import fi.triforce.TicketGuru.Domain.TicketRepository;
import fi.triforce.TicketGuru.Domain.TicketType;
import fi.triforce.TicketGuru.Domain.TicketTypeRepository;
import fi.triforce.TicketGuru.dto.SalesObject;
import fi.triforce.TicketGuru.exception.ResourceNotFoundException;
import fi.triforce.TicketGuru.exception.TooManyTicketsException;
import fi.triforce.TicketGuru.utils.EntityValidation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import fi.triforce.TicketGuru.Domain.Event;
import fi.triforce.TicketGuru.Domain.SalesEvent;
import java.math.BigDecimal;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SalesService {

    @Autowired
    private TicketTypeRepository ttr;

    @Autowired
    private SalesEventRepository sr;

    @Autowired
    private TicketRepository tr;

    @Autowired
    private Validator validator;

    private int discountTicketsLeft;

    public SalesEvent createSale(List<SalesObject> sale) {

        for (SalesObject s : sale) {
            EntityValidation.validateEntity(validator, s);
        }
        SalesEvent receipt = createTicketsFromSalesObjects(sale);
        return receipt;
    }

    private SalesEvent createTicketsFromSalesObjects(List<SalesObject> sale) throws ResourceNotFoundException {
        SalesEvent newSale = new SalesEvent();
        Map<String, Integer> eventTicketsSold = new HashMap();
        for (int i = 0; i < sale.size(); i++) {
            SalesObject salesObject = sale.get(i);
            TicketType tt = ttr.findById(salesObject.getTicketTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cannot find a tickettype with the id " + salesObject.getTicketTypeId()));
            // Ticketin m????r??n tarkistus, heitt???? errorin jos menee pieleen
            Event event = tt.getEvent();
            Long eventMaxTickets = event.getNumberOfTickets();
            String eventTitle = event.getEventTitle();
            eventTicketsSold.putIfAbsent(eventTitle, 0);
            List<Ticket> ticketsSoldWithTicketType = tr.findByTicketType(tt);
            eventTicketsSold.put(eventTitle, eventTicketsSold.get(eventTitle)
                    + (salesObject.getNrOfTickets() + ticketsSoldWithTicketType.size()));
            if (eventTicketsSold.get(eventTitle) > eventMaxTickets) {
                throw new TooManyTicketsException(
                        "This sale exceeds the max amount of tickets allowed for the event " + eventTitle + " ("
                                + eventMaxTickets + " tickets max, exceeding by "
                                + (eventTicketsSold.get(eventTitle) - eventMaxTickets) + " tickets)");

            }
            // Tarkistus p????ttyy
            discountTicketsLeft = salesObject.getNrOfDiscounted();
            discountTicketsLeft = salesObject.getNrOfDiscounted();

            for (int o = 0; o < salesObject.getNrOfTickets(); o++) {
                Ticket ticket = new Ticket();
                ticket.setTicketSale(newSale);
                ticket.setTicketType(tt);
                ticket.setTicketUsed(false);
                if (discountTicketsLeft > 0) {
                    discountTicketsLeft--;
                    ticket.setFinalPrice(tt.getPrice()
                            .multiply(BigDecimal.valueOf(1).subtract(salesObject.getDiscountPercentage()))); // BigDecimal
                                                                                                             // tarvitsee
                                                                                                             // oman
                                                                                                             // laskutoimitussyntaksin.
                                                                                                             // +,-,*,/
                                                                                                             // eiv??t
                                                                                                             // k??y.
                } else {
                    ticket.setFinalPrice(tt.getPrice());
                }
                sr.save(newSale);
                ticket.generateTicketCode();
                newSale.addTicket(ticket);
                tr.save(ticket);
            }

        }

        newSale.setDateOfSale(LocalDateTime.now());
        sr.save(newSale);
        return newSale;
    }

    public SalesEvent getSalesEvent(Long id) {
        SalesEvent salesEvent = sr.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find a sales event with the id " + id));
        return salesEvent;
    }

    public List<SalesEvent> getAllSalesEvents() {
        return sr.findAll();
    }
}
