package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.bookingDTO.BookingRequestDTO;
import com.busticket.busticketbooking.dto.bookingDTO.BookingResponseDTO;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Trip;

// Mapper class for Booking entity and DTO conversion
public class BookingMapper {

    // Converts BookingRequestDTO to Booking Entity
    public static Booking mapToEntity(
            BookingRequestDTO bookingRequestDTO,
            Trip trip
    ) {

        // Create new Booking entity object
        Booking booking = new Booking();

        // Set trip details
        booking.setTrip(trip);

        // Set seat number
        booking.setSeatNumber(bookingRequestDTO.getSeatNumber());

        // Set booking status
        booking.setStatus(bookingRequestDTO.getStatus());

        return booking;
    }

    // Converts Booking Entity to BookingResponseDTO
    public static BookingResponseDTO mapToResponseDTO(
            Booking booking
    ) {

        return new BookingResponseDTO(
                booking.getId(),
                booking.getTrip().getId(),
                booking.getSeatNumber(),
                booking.getStatus()
        );
    }
}