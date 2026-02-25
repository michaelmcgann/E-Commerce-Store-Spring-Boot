package com.sbeccommerce.ecommercestore.global.common.exception;

import com.sbeccommerce.ecommercestore.global.common.DTO.APIResponse;
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
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException exception) {

        String message = exception.getMessage();
        APIResponse response = new APIResponse(message, false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myApiException(APIException exception) {
        String message = exception.getMessage();
        APIResponse response = new APIResponse(message, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
