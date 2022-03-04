package fi.triforce.TicketGuru.Domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SalesObject {
	
	private HashMap<Long, HashMap<Long, Integer>> events;
	private int totalTickets;
	
	

}
