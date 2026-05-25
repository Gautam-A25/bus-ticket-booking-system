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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// Service layer handles booking business logic
import org.springframework.transaction.annotation.Transactional;

// Marks this class as Service layer component
@Service
public class BookingServiceImpl implements BookingService {

    // Repository object for booking database operations
    private final BookingRepo bookingRepo;

    // Repository object for trip database operations
    private final TripRepo tripRepo;

    // Repository object for payment database operations
    private final PaymentRepo paymentRepo;

    // Constructor injection for dependency injection
    public BookingServiceImpl(
            BookingRepo bookingRepo,
            TripRepo tripRepo,
            PaymentRepo paymentRepo
    ) {
        this.bookingRepo = bookingRepo;
        this.tripRepo = tripRepo;
        this.paymentRepo = paymentRepo;
    }

    // Creates a new booking for a trip
    @Override
    public BookingResponseDTO createBooking(
            Integer tripId,
            BookingRequestDTO bookingRequestDTO
    ) {

        // Fetches trip using trip ID
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Trip with ID " + tripId + " not found"
                        ) );

        // Validates seat number against bus capacity
        if (
                trip.getBus() != null &&
                        trip.getBus().getCapacity() != null &&
                        bookingRequestDTO.getSeatNumber() >
                                trip.getBus().getCapacity()
        ) {
            throw new InvalidOperationException(
                    "Seat number cannot exceed bus capacity of "
                            + trip.getBus().getCapacity()
            );
        }

        // Checks whether seat is already booked
        if (
                bookingRepo.existsByTripIdAndSeatNumber(
                        tripId,
                        bookingRequestDTO.getSeatNumber()
                )
        ) {

            throw new DuplicateResourceException(
                    "Seat " +
                            bookingRequestDTO.getSeatNumber() +
                            " is already booked for Trip ID " +
                            tripId
            );
        }

        // Checks whether seats are available in trip
        if (
                trip.getAvailableSeats() != null &&
                        trip.getAvailableSeats() <= 0
        ) {

            throw new InvalidOperationException(
                    "No available seats left on Trip with ID " +
                            tripId
            );
        }

        // Converts DTO object into entity object
        Booking booking = BookingMapper.mapToEntity(
                bookingRequestDTO,
                trip
        );

        // Saves booking into database
        Booking savedBooking = bookingRepo.save(booking);

        // Converts entity into response DTO
        return BookingMapper.mapToResponseDTO(savedBooking);
    }

    // Fetches booking records using pagination
    @Override
    public Page<BookingResponseDTO> getBookingPage(int page, int size) {

        // Creates pageable object using page number and size
        Pageable pageable = PageRequest.of(page, size);

        return bookingRepo
                .findAll(pageable)

                // Converts entity objects into response DTOs
                .map(BookingMapper::mapToResponseDTO);
    }

    // Fetches all bookings of a customer
    @Override
    public List<BookingResponseDTO> getBookingsByCustomer(
            Integer customerId
    ) {

        return paymentRepo.findByCustomerId(customerId)
                .stream()

                // Fetches booking object from payment
                .map(Payment::getBooking)

                // Converts entity into response DTO
                .map(BookingMapper::mapToResponseDTO)

                .toList();
    }

    // Fetches booking details using booking ID
    @Override
    public BookingResponseDTO getBookingById(
            Integer bookingId
    ) {

        // Fetches booking using booking ID
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking with ID " + bookingId + " not found"));

        // Converts entity into response DTO
        return BookingMapper.mapToResponseDTO(booking);
    }

    // Cancels existing booking
    @Override
    @Transactional
    public String cancelBooking(
            Integer bookingId
    ) {

        // Fetches booking using booking ID
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking with ID " + bookingId + " not found"));
// Creates booking cancellation message
String bookingDetails =
        "Booking Cancelled Successfully : ID = "
                + booking.getId()
                + ", Trip ID = "
                + (booking.getTrip() != null
                        ? booking.getTrip().getId()
                        : null)
                + ", Seat Number = "
                + booking.getSeatNumber()
                + ", Status = "
                + booking.getStatus();

        // Cascade delete: delete associated payment if it exists
        paymentRepo.findByBookingId(bookingId).ifPresent(paymentRepo::delete);

        // Deletes booking from database
        bookingRepo.delete(booking);

        return bookingDetails;
    }
}