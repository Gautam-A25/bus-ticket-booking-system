package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.BookingDTO.BookingRequestDTO;
import com.busticket.busticketbooking.dto.BookingDTO.BookingResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface defining all booking management operations.
 *
 * <p>A {@code Booking} registers a seat reservation on a specific {@code Trip}.
 * Booking actions are closely tied to {@code Payment} records. Canceling a booking
 * will cascade delete its associated payment and free up the seat slot on the trip.</p>
 */
public interface BookingService {

    /**
     * Creates and persists a new booking for the given trip.
     *
     * @param tripId            ID of the trip being booked
     * @param bookingRequestDTO booking details such as seat number and status
     * @return a {@link BookingResponseDTO} representing the created booking
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the trip is not found
     * @throws com.busticket.busticketbooking.exception.InvalidOperationException if the seat number is invalid
     * @throws com.busticket.busticketbooking.exception.DuplicateResourceException if the seat is already booked
     */
    BookingResponseDTO createBooking(
            Integer tripId,
            BookingRequestDTO bookingRequestDTO
    );

    /**
     * Retrieves a paginated slice of all bookings.
     *
     * @param page zero-based page index
     * @param size maximum number of bookings per page
     * @return a {@link Page} of booking response DTOs
     */
    Page<BookingResponseDTO> getBookingPage(int page, int size);

    /**
     * Retrieves all booking records associated with a specific customer.
     *
     * @param customerId ID of the customer
     * @return a list of bookings associated with the customer
     */
    List<BookingResponseDTO> getBookingsByCustomer(
            Integer customerId
    );

    /**
     * Retrieves booking details by its ID.
     *
     * @param bookingId ID of the booking to retrieve
     * @return the booking response DTO
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the booking is not found
     */
    BookingResponseDTO getBookingById(
            Integer bookingId
    );

    /**
     * Cancels an existing booking and cascade deletes any associated payment record.
     *
     * @param bookingId ID of the booking to cancel
     * @return a formatted cancellation summary message
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the booking is not found
     */
    String cancelBooking(
            Integer bookingId
    );
}