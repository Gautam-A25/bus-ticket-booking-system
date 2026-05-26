package com.busticket.busticketbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Bus Ticket Booking System.
 *
 * <p>This Spring Boot application provides REST APIs and a Thymeleaf-based
 * web UI for managing buses, routes, trips, bookings, payments, reviews,
 * drivers, customers, and agencies.</p>
 *
 * <p>Key features:</p>
 * <ul>
 *   <li>JWT-secured REST API at {@code /api/v1/**}</li>
 *   <li>Session-based Thymeleaf web UI at {@code /ui/**} and {@code /modules/**}</li>
 *   <li>OpenAPI/Swagger documentation at {@code /swagger-ui.html}</li>
 * </ul>
 */
@SpringBootApplication
public class BusTicketBookingApplication {

    /**
     * Bootstraps the Spring application context and starts the embedded server.
     *
     * @param args command-line arguments passed at startup (not used)
     */
    public static void main(String[] args) {
        SpringApplication.run(BusTicketBookingApplication.class, args);
    }
}