package fi.triforce.TicketGuru.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import fi.triforce.TicketGuru.dto.SalesObject;
import fi.triforce.TicketGuru.exception.ValidationException;

public class EntityValidation {

    public static void validateEntity(Validator validator, Object obj) {
        Set<ConstraintViolation<Object>> result = validator.validate(obj);
            if (!result.isEmpty()) {
                String errorMsg = result.toString();
                String[] splitMsg = errorMsg.split("=");
                String propertyName = splitMsg[2].split(",")[0] + " " + splitMsg[1].split(",")[0].replace("'", "");
                throw new ValidationException(propertyName);
            }
    }
    
}
