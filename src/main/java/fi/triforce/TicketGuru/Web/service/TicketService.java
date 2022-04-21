package fi.triforce.TicketGuru.Web.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import fi.triforce.TicketGuru.Domain.EventRepository;
import fi.triforce.TicketGuru.Domain.Ticket;
import fi.triforce.TicketGuru.Domain.TicketRepository;
import fi.triforce.TicketGuru.Domain.TicketType;
import fi.triforce.TicketGuru.Domain.TicketTypeRepository;
import fi.triforce.TicketGuru.exception.NotFoundException;
import fi.triforce.TicketGuru.exception.ResourceNotFoundException;
import fi.triforce.TicketGuru.exception.TicketUsedException;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicketService {
    @Autowired
    private EventRepository er;
    @Autowired
    private TicketTypeRepository ttr;
    @Autowired
    private TicketRepository tr;

    public List<Ticket> getTicketsByEventId(Long eventId) {
        er.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find an event with the id " + eventId));
        return listTicketsforEvent(eventId);
    }

    // Inserted eventid must be confirmed
    private List<Ticket> listTicketsforEvent(Long eventId) {
        List<TicketType> types = ttr.findByEvent(er.findById(eventId).get());
        List<Ticket> tickets = new ArrayList<Ticket>();
        for (int i = 0; i < types.size(); i++) {
            tickets.addAll(tr.findByTicketType(types.get(i)));
        }
        return tickets;
    }

    public Ticket singleTicketByTicketCode(String ticketCode) {
        Ticket ticket = tr.findByTicketCode(ticketCode);
        if (ticket == null) {
            throw new NotFoundException("No ticket found with the code " + ticketCode);
        }
        return ticket;
    }

    public Ticket setTicketUsed(Ticket t) {
        String ticketCode = t.getTicketCode();
        Ticket ticket = tr.findByTicketCode(ticketCode);
        if (ticket == null) {
            throw new NotFoundException("No ticket found with the code " + ticketCode);
        } else if (ticket.getTicketUsed()) {
            throw new TicketUsedException("A ticket with the code " + ticketCode + " has already been used");
        }
        ticket.setTicketUsed(true);
        return tr.save(ticket);
    }
}
