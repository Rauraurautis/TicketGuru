package fi.triforce.TicketGuru;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.triforce.TicketGuru.Domain.Role;
import fi.triforce.TicketGuru.Domain.User;
import fi.triforce.TicketGuru.Web.service.UserService;

@SpringBootApplication
public class TicketGuruApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketGuruApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run (UserService us) {
		return args -> {
			us.saveRole(new Role(null, "ROLE_ADMIN"));
			us.saveRole(new Role(null, "ROLE_SALES"));
			us.saveRole(new Role(null, "ROLE_TICKETINSPECTOR"));

			us.saveUser(new User(null, "John Travolta", "John", "1234", new ArrayList<>()));
			us.saveUser(new User(null, "Matt Thomson", "matt", "1234", new ArrayList<>()));
			us.saveUser(new User(null, "Mary Smith", "m_smith", "1234", new ArrayList<>()));

			us.addRoleToUser("John", "ROLE_ADMIN");
			us.addRoleToUser("matt", "ROLE_SALES");
			us.addRoleToUser("m_smith", "ROLE_TICKETINSPECTOR");
	
		};
	}

	
}
