package fi.triforce.TicketGuru.Web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.triforce.TicketGuru.Web.service.SalesService;
import fi.triforce.TicketGuru.dto.SalesObject;

import fi.triforce.TicketGuru.exception.ValidationException;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

	@Autowired
	SalesService ss;

	@PostMapping
	public ResponseEntity<?> makeASaleRest(@RequestBody List<SalesObject> sale) throws ValidationException {
		return new ResponseEntity(ss.createSale(sale), HttpStatus.CREATED);
	}

}
