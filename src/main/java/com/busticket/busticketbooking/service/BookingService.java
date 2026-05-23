package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.BookingDTO.BookingRequestDTO;
import com.busticket.busticketbooking.dto.BookingDTO.BookingResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

// Service interface for Booking operations
public interface BookingService {

    // Method to create booking
    BookingResponseDTO createBooking(
            Integer tripId,
            BookingRequestDTO bookingRequestDTO
    );

    Page<BookingResponseDTO> getBookingPage(int page, int size);

    // Method to get bookings by customer ID
    List<BookingResponseDTO> getBookingsByCustomer(
            Integer customerId
    );

    // Method to get booking by booking ID
    BookingResponseDTO getBookingById(
            Integer bookingId
    );

    // Method to cancel booking
    String cancelBooking(
            Integer bookingId
    );
}