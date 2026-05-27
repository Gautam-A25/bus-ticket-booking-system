package com.busticket.busticketbooking.exception;

/**
 * Exception thrown when a resource creation fails due to a duplicate uniqueness violation
 * (e.g., trying to register an email, license number, or seat number that already exists).
 */
public class DuplicateResourceException extends RuntimeException {
    /**
     * Constructs a new DuplicateResourceException with the specified detail message.
     *
     * @param message the detail error message
     */
    public DuplicateResourceException(String message) {
        super(message);
    }
}
