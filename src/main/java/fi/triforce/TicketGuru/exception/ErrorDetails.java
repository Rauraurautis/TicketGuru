package fi.triforce.TicketGuru.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private LocalDateTime timeStamp;
    private HttpStatus httpStatus;
    private String message;
}
