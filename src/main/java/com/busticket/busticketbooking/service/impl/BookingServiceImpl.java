package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.bookingDTO.BookingRequestDTO;
import com.busticket.busticketbooking.dto.bookingDTO.BookingResponseDTO;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Payment;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.mapper.BookingMapper;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.service.BookingService;
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
                        new RuntimeException("Trip not found"));

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
                        new RuntimeException("Booking not found"));

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
                        new RuntimeException("Booking not found"));

        // Delete booking from database
        bookingRepo.delete(booking);

        return "Booking with ID " + bookingId +
                " cancelled successfully";
    }
}