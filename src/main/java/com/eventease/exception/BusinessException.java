package com.eventease.exception;

// This exception is thrown when a business rule is violated
// Examples:
// - Event is full (no available seats)
// - Student already registered for the event
// - Cancellation after event date
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
