package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.bookingDTO.BookingRequestDTO;
import com.busticket.busticketbooking.dto.bookingDTO.BookingResponseDTO;
import com.busticket.busticketbooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Marks this class as REST Controller
@RestController

// Base URL for Booking APIs
@RequestMapping("/api/v1")
public class BookingController {

    // Service layer dependency
    private final BookingService bookingService;

    // Constructor Injection
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // API to create booking for a trip
    @PostMapping("/trips/{tripId}/bookings")
    public BookingResponseDTO createBooking(
            @PathVariable Integer tripId,
            @Valid @RequestBody BookingRequestDTO bookingRequestDTO) {

        return bookingService.createBooking(
                tripId,
                bookingRequestDTO
        );
    }

    // API to get all bookings of a customer
    @GetMapping("/customers/{customerId}/bookings")
    public List<BookingResponseDTO> getBookingsByCustomer(
            @PathVariable Integer customerId) {

        return bookingService.getBookingsByCustomer(customerId);
    }

    // API to get booking details by booking ID
    @GetMapping("/bookings/{bookingId}")
    public BookingResponseDTO getBookingById(
            @PathVariable Integer bookingId) {

        return bookingService.getBookingById(bookingId);
    }

    // API to cancel booking
    @PatchMapping("/bookings/{bookingId}/cancel")
    public String cancelBooking(
            @PathVariable Integer bookingId) {

        return bookingService.cancelBooking(bookingId);
    }
}