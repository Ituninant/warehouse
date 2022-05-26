package ru.warehouse.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;
import java.util.Map;

@RestControllerAdvice
public class ApiAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleENFE(EntityNotFoundException e) {
        return Map.of(
                "timestamp", ZonedDateTime.now(),
                "status", 404,
                "error", e.getMessage()
        );
    }

}
