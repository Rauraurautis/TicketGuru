package fi.triforce.TicketGuru.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TooManyTicketsException extends RuntimeException {

    public TooManyTicketsException(String message) {
        super(message);
    }

}
