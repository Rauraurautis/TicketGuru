package fi.triforce.TicketGuru.Web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.triforce.TicketGuru.Domain.Event;
import fi.triforce.TicketGuru.Domain.EventRepository;

@Controller
public class EventRestController {
	@Autowired
	private EventRepository repository;
	
	@GetMapping("/events")
	public @ResponseBody List<Event> eventListRest() {
		return (List<Event>) repository.findAll();
	}

}
