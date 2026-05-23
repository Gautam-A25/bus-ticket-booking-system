package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.BookingDTO.BookingRequestDTO;
import com.busticket.busticketbooking.dto.BookingDTO.BookingResponseDTO;
import com.busticket.busticketbooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller layer handles booking-related HTTP requests
@RestController

// Base URL for all booking APIs
@RequestMapping("/api/v1")
public class BookingController {

    // Service layer object used for booking business logic
    private final BookingService bookingService;

    // Constructor injection for dependency injection
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Creates a new booking for the given trip
    @PostMapping("/trips/{tripId}/bookings")
    public BookingResponseDTO createBooking(
            @PathVariable Integer tripId,
            @Valid @RequestBody BookingRequestDTO bookingRequestDTO) {

        return bookingService.createBooking(
                tripId,
                bookingRequestDTO
        );
    }

    // Fetches all bookings of a specific customer
    @GetMapping("/customers/{customerId}/bookings")
    public List<BookingResponseDTO> getBookingsByCustomer(
            @PathVariable Integer customerId) {

        return bookingService.getBookingsByCustomer(customerId);
    }

    // Fetches booking details using booking ID
    @GetMapping("/bookings/{bookingId}")
    public BookingResponseDTO getBookingById(
            @PathVariable Integer bookingId) {

        return bookingService.getBookingById(bookingId);
    }

    // Cancels an existing booking
    @PatchMapping("/bookings/{bookingId}/cancel")
    public String cancelBooking(
            @PathVariable Integer bookingId) {

        return bookingService.cancelBooking(bookingId);
    }
}