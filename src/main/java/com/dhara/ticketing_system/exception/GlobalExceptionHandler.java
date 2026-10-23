package com.dhara.ticketing_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Object> handleDuplicateEmail(DuplicateEmailException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " + ex.getMessage());
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(InvalidAssignmentException.class)
    public ResponseEntity<Object> handleInvalidAssignment(InvalidAssignmentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ResponseEntity<Object> handleInvalidTransition(InvalidStatusTransitionException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentials(Exception ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Failed");
        body.put("message", fieldErrors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.security.authentication.DisabledException.class)
    public ResponseEntity<Object> handleDisabledUser(Exception ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "This account has been deactivated");
    }
}
