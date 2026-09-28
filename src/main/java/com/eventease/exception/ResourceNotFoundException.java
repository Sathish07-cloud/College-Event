package com.eventease.exception;

// This exception is thrown when a requested resource is not found in the database
// For example: Student not found, Event not found, Registration not found
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
