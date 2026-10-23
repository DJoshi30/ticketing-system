package com.dhara.ticketing_system.exception;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(String message) {
        super(message);
    }
}