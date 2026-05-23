package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.BookingDTO.BookingRequestDTO;
import com.busticket.busticketbooking.dto.BookingDTO.BookingResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

// Service interface defines booking business operations
public interface BookingService {

    // Creates a new booking for a trip
    BookingResponseDTO createBooking(
            Integer tripId,
            BookingRequestDTO bookingRequestDTO
    );

    // Fetches booking records using pagination
    Page<BookingResponseDTO> getBookingPage(int page, int size);

    // Fetches all bookings of a specific customer
    List<BookingResponseDTO> getBookingsByCustomer(
            Integer customerId
    );

    // Fetches booking details using booking ID
    BookingResponseDTO getBookingById(
            Integer bookingId
    );

    // Cancels an existing booking
    String cancelBooking(
            Integer bookingId
    );
}