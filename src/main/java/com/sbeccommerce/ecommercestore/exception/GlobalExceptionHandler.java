package com.sbeccommerce.ecommercestore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        Map<String, String> response = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach( error -> {
            String key = (error instanceof FieldError fieldError) ? fieldError.getField() : error.getObjectName();
            response.put(key, error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> myResourceNotFoundException(ResourceNotFoundException exception) {

        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> myApiException(APIException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
