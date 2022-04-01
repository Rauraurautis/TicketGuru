package fi.triforce.TicketGuru.Web.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import fi.triforce.TicketGuru.Domain.SalesEventRepository;
import fi.triforce.TicketGuru.Domain.Ticket;
import fi.triforce.TicketGuru.Domain.TicketRepository;
import fi.triforce.TicketGuru.Domain.TicketType;
import fi.triforce.TicketGuru.Domain.TicketTypeRepository;
import fi.triforce.TicketGuru.dto.SalesObject;
import fi.triforce.TicketGuru.exception.ResourceNotFoundException;
import fi.triforce.TicketGuru.exception.ValidationException;
import fi.triforce.TicketGuru.Domain.SalesEvent;
import java.math.BigDecimal;

@Service
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
            Set<ConstraintViolation<SalesObject>> result = validator.validate(s);
            if (!result.isEmpty()) {
                String errorMsg = result.toString();
                String[] splitMsg = errorMsg.split("=");
                String propertyName = splitMsg[2].split(",")[0] + " " + splitMsg[1].split(",")[0].replace("'", "");
                throw new ValidationException(propertyName);
            }
        }
        SalesEvent receipt = createTicketsFromSalesObjects(sale);
        return receipt;
    }

    private SalesEvent createTicketsFromSalesObjects(List<SalesObject> sale) throws ResourceNotFoundException {
        SalesEvent newSale = sr.save(new SalesEvent());
        for (int i = 0; i < sale.size(); i++) {
            SalesObject salesObject = sale.get(i);
            TicketType tt = ttr.findById(salesObject.getTicketTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cannot find a tickettype with the id " + salesObject.getTicketTypeId()));

            discountTicketsLeft = salesObject.getNrOfDiscounted();

            for (int o = 0; o < salesObject.getNrOfTickets(); o++) {
                Ticket ticket = new Ticket();
                ticket.setTicketSale(newSale);
                ticket.setTicketType(tt);
                ticket.setTicketUsed(false);
                if (discountTicketsLeft > 0) {
                    discountTicketsLeft--;
                    ticket.setFinalPrice(tt.getPrice().multiply(BigDecimal.valueOf(1).subtract(salesObject.getDiscountPercentage())));  //bigDecimal tarvitsee oman laskutoimitussyntaksin. +,-,*,/ eivät käy.
                } else {
                    ticket.setFinalPrice(tt.getPrice());
                }
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
