package com.example.spring_security_jwt.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    /**
     * Exception for handling same type of handled exception coming from different controllers
     * @param exception
     * @return ResponseEntity with custom message
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException exception) {
        return new ResponseEntity<>(exception.getMessage() +" "+ exception.getStatus(), exception.getStatus());
    }
}
