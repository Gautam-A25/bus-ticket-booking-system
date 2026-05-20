package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.BookingDTO.BookingRequestDTO;
import com.busticket.busticketbooking.dto.BookingDTO.BookingResponseDTO;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Payment;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.mapper.BookingMapper;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.service.BookingService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.exception.DuplicateResourceException;
import com.busticket.busticketbooking.exception.InvalidOperationException;
import org.springframework.stereotype.Service;

import java.util.List;

// Marks this class as Service layer component
@Service
public class BookingServiceImpl implements BookingService {

    // Repository dependency for Booking table
    private final BookingRepo bookingRepo;

    // Repository dependency for Trip table
    private final TripRepo tripRepo;

    // Repository dependency for Payment table
    private final PaymentRepo paymentRepo;

    // Constructor Injection
    public BookingServiceImpl(
            BookingRepo bookingRepo,
            TripRepo tripRepo,
            PaymentRepo paymentRepo
    ) {
        this.bookingRepo = bookingRepo;
        this.tripRepo = tripRepo;
        this.paymentRepo = paymentRepo;
    }

    // Method to create booking
    @Override
    public BookingResponseDTO createBooking(
            Integer tripId,
            BookingRequestDTO bookingRequestDTO
    ) {

        // Fetch trip by ID
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Trip with ID " + tripId + " not found"));

        if (bookingRepo.existsByTripIdAndSeatNumber(tripId, bookingRequestDTO.getSeatNumber())) {
            throw new DuplicateResourceException("Seat " + bookingRequestDTO.getSeatNumber() + " is already booked for Trip ID " + tripId);
        }

        if (trip.getAvailableSeats() != null && trip.getAvailableSeats() <= 0) {
            throw new InvalidOperationException("No available seats left on Trip with ID " + tripId);
        }

        // Convert DTO to Entity
        Booking booking = BookingMapper.mapToEntity(
                bookingRequestDTO,
                trip
        );

        // Save booking into database
        Booking savedBooking = bookingRepo.save(booking);

        // Convert Entity to Response DTO
        return BookingMapper.mapToResponseDTO(savedBooking);
    }

    // Method to get all bookings of a customer
    @Override
    public List<BookingResponseDTO> getBookingsByCustomer(
            Integer customerId
    ) {

        return paymentRepo.findByCustomerId(customerId)
                .stream()

                // Get booking from payment
                .map(Payment::getBooking)

                // Convert Entity to Response DTO
                .map(BookingMapper::mapToResponseDTO)

                .toList();
    }

    // Method to get booking by booking ID
    @Override
    public BookingResponseDTO getBookingById(
            Integer bookingId
    ) {

        // Fetch booking by ID
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking with ID " + bookingId + " not found"));

        // Convert Entity to Response DTO
        return BookingMapper.mapToResponseDTO(booking);
    }

    // Method to cancel booking
    @Override
    public String cancelBooking(
            Integer bookingId
    ) {

        // Fetch booking by ID
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking with ID " + bookingId + " not found"));

        // Booking cancel message
        String bookingDetails =
                "Booking Cancelled Successfully : " +
                        "ID = " + booking.getId() +
                        ", Trip ID = " + booking.getTrip().getId() +
                        ", Seat Number = " + booking.getSeatNumber() +
                        ", Status = " + booking.getStatus();

        // Delete booking from database
        bookingRepo.delete(booking);

        return bookingDetails;
    }
}