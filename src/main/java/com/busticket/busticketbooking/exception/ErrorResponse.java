package com.busticket.busticketbooking.exception;

import java.time.LocalDateTime;

/**
 * Representation model of an API error response.
 *
 * <p>Used to wrap error payloads returned by the {@link GlobalExceptionHandler} to clients.</p>
 */
public class ErrorResponse {
    /** The timestamp when the error occurred. */
    private LocalDateTime timestamp;
    /** The error message description. */
    private String message;
    /** Additional context or URI endpoint path details of the error request. */
    private String details;

    /**
     * Default no-argument constructor.
     */
    public ErrorResponse() {
    }

    /**
     * Constructs a fully-initialized ErrorResponse.
     *
     * @param timestamp the time the error occurred
     * @param message   the error description message
     * @param details   additional request detail context
     */
    public ErrorResponse(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    /**
     * Retrieves the error occurrence timestamp.
     *
     * @return the error timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the error occurrence timestamp.
     *
     * @param timestamp the error timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the error message description.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message description.
     *
     * @param message the error message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retrieves additional context or request detail endpoint path.
     *
     * @return additional request details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets additional context or request detail endpoint path.
     *
     * @param details additional request details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }
}
