package fi.triforce.TicketGuru.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<?> resourceNotFoundHandler(ResourceNotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), httpStatus,
                exception.getMessage());
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    @ExceptionHandler({ ValidationException.class })
    public ResponseEntity<?> validationExceptionHandler(ValidationException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), httpStatus,
                exception.getMessage());
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<?> notFoundExceptionHandler(NotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), httpStatus,
                exception.getMessage());
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    @ExceptionHandler({ MethodNotAllowedException.class })
    public ResponseEntity<?> MethodNotAllowedExceptionHandler(MethodNotAllowedException exception) {
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), httpStatus,
                exception.getMessage());
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    @ExceptionHandler({ TicketUsedException.class })
    public ResponseEntity<?> TicketUsedExceptionHandler(TicketUsedException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), httpStatus,
                exception.getMessage());
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    @ExceptionHandler({ ForbiddenException.class })
    public ResponseEntity<?> ForbiddenExceptionHandler(ForbiddenException exception) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), httpStatus,
                exception.getMessage());
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    @ExceptionHandler({ TooManyTicketsException.class })
    public ResponseEntity<?> TooManyTicketsExceptionHandler(TooManyTicketsException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), httpStatus,
                exception.getMessage());
        return new ResponseEntity<>(errorDetails, httpStatus);
    }
}
