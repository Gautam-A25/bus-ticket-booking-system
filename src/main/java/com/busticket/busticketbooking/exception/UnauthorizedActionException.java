package com.busticket.busticketbooking.exception;

/**
 * Exception thrown when a user attempts to perform an operation they are not authorized to do
 * (e.g., trying to submit reviews for trips that have not yet departed).
 */
public class UnauthorizedActionException extends RuntimeException {
    /**
     * Constructs a new UnauthorizedActionException with the specified detail message.
     *
     * @param message the detail error message
     */
    public UnauthorizedActionException(String message) {
        super(message);
    }
}
