package fi.triforce.TicketGuru.Web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.triforce.TicketGuru.Domain.SalesEvent;
import fi.triforce.TicketGuru.Domain.SalesEventRepository;

@RestController
@RequestMapping("/api/salesevents")
public class SalesEventRestController {

    @Autowired
    private SalesEventRepository sr;

    @GetMapping
    public List<SalesEvent> getAllSalesEvents() {
        return sr.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesEvent> salesEventGetSingleRest(@PathVariable(name = "id") Long id)
            throws ResourceNotFoundException {
        SalesEvent salesEvent = sr.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find a sales event with the id " + id));
        return ResponseEntity.ok(salesEvent);
    }

}
