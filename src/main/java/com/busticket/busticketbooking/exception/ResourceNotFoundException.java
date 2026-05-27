package com.busticket.busticketbooking.exception;

/**
 * Exception thrown when a requested resource (e.g., Trip, Customer, Agency, Booking,
 * Payment, or Address) cannot be found by its unique identifier in the database.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
