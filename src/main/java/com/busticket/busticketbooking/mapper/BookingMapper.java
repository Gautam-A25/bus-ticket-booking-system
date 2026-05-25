package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.BookingDTO.BookingRequestDTO;
import com.busticket.busticketbooking.dto.BookingDTO.BookingResponseDTO;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Trip;

// Mapper class used for Booking entity and DTO conversion
public class BookingMapper {

    // Converts BookingRequestDTO into Booking entity object
    public static Booking mapToEntity(
            BookingRequestDTO bookingRequestDTO,
            Trip trip
    ) {

        // Creates new Booking entity object
        Booking booking = new Booking();

        // Sets associated trip details
        booking.setTrip(trip);

        // Sets selected seat number
        booking.setSeatNumber(bookingRequestDTO.getSeatNumber());

        // Sets booking status
        booking.setStatus(bookingRequestDTO.getStatus());

        return booking;
    }

    // Converts Booking entity into BookingResponseDTO
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