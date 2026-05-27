package com.busticket.busticketbooking.exception;

/**
 * Exception thrown when a payment transaction fails or is declined by the gateway processor.
 */
public class PaymentFailedException extends RuntimeException {
    /**
     * Constructs a new PaymentFailedException with the specified detail message.
     *
     * @param message the detail error message
     */
    public PaymentFailedException(String message) {
        super(message);
    }
}
