package fi.triforce.TicketGuru.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.triforce.TicketGuru.utils.ReturnMsg;

@Controller
public class FalseUrlController extends AbstractErrorController {

    public FalseUrlController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping("/error")
    public ResponseEntity<?> handleErrorRequest(HttpServletRequest req) {
        HttpStatus status = getStatus(req);
        if (status.equals(HttpStatus.NOT_FOUND)) {
            System.out.println(req.getRequestURI());
            throw new NotFoundException("You probably specified a false url");
        } else if (status.equals(HttpStatus.METHOD_NOT_ALLOWED)) {
            throw new MethodNotAllowedException("You probably used a method on an url that does not support the method");
        }

        return new ResponseEntity<>(new ReturnMsg(status.toString() +": Something went wrong").getReturnMsg(), status);

    }
}
