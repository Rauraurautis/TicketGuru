package fi.triforce.TicketGuru.Web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.triforce.TicketGuru.Domain.SalesEvent;
import fi.triforce.TicketGuru.Web.service.SalesService;
import fi.triforce.TicketGuru.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/salesevents")
public class SalesEventRestController {

    @Autowired
    private SalesService ss;

    @GetMapping
    public ResponseEntity<?> getAllSalesEvents() {
        return ResponseEntity.ok(ss.getAllSalesEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesEvent> salesEventGetSingleRest(@PathVariable(name = "id") Long id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(ss.getSalesEvent(id));
    }

}
