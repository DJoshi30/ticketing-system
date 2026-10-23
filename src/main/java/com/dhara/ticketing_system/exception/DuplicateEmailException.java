package com.dhara.ticketing_system.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}