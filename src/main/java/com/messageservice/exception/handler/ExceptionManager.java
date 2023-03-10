package com.messageservice.exception.handler;

import com.messageservice.exception.ErrorResponse;
import com.messageservice.exception.message.MessageNotFoundException;
import com.messageservice.exception.message.RecipientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionManager{

    @ExceptionHandler({
            MessageNotFoundException.class,
            RecipientNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(Throwable e) {
        var response = new ErrorResponse();
        response.setError("Resource not found");
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(e.getMessage());
        response.setTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        var response = new ErrorResponse();
        response.setError("Generic Error");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Generic Error");
        response.setTime(LocalDateTime.now());
        return ResponseEntity.internalServerError().body(response);
    }
}
