package com.busticket.busticketbooking.exception;

/**
 * Exception thrown when an action or business operation cannot be completed due to
 * invalid business rules or constraints (e.g., booking seats exceeding bus capacity,
 * or trying to review a trip that has not departed yet).
 */
public class InvalidOperationException extends RuntimeException {
    /**
     * Constructs a new InvalidOperationException with the specified detail message.
     *
     * @param message the detail error message
     */
    public InvalidOperationException(String message) {
        super(message);
    }
}
