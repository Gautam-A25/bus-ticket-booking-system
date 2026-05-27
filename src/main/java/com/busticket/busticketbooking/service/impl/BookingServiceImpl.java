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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Concrete implementation of {@link BookingService}.
 *
 * <p>Handles the core transaction flow of reserving bus seats for trips.
 * Ensures the seat number doesn't exceed bus capacity, validates seat uniqueness
 * per trip, and enforces available seat limitations. Provides a transactional method for
 * booking cancellation which cascades to the linked payment.</p>
 */
@Service
public class BookingServiceImpl implements BookingService {

    /** Repository for performing booking database operations. */
    private final BookingRepo bookingRepo;

    /** Repository for performing trip database operations. */
    private final TripRepo tripRepo;

    /** Repository for performing payment database operations. */
    private final PaymentRepo paymentRepo;

    /**
     * Constructs a BookingServiceImpl with required repository dependencies.
     *
     * @param bookingRepo repository for booking data access
     * @param tripRepo    repository for trip data access
     * @param paymentRepo repository for payment data access
     */
    public BookingServiceImpl(
            BookingRepo bookingRepo,
            TripRepo tripRepo,
            PaymentRepo paymentRepo
    ) {
        this.bookingRepo = bookingRepo;
        this.tripRepo = tripRepo;
        this.paymentRepo = paymentRepo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookingResponseDTO createBooking(
            Integer tripId,
            BookingRequestDTO bookingRequestDTO
    ) {
        // Fetch trip and throw exception if not found
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Trip with ID " + tripId + " not found"
                        ));

        // Enforce seat number capacity checks
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

        // Verify that the requested seat isn't already booked
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

        // Enforce seat availability limits
        if (
                trip.getAvailableSeats() != null &&
                        trip.getAvailableSeats() <= 0
        ) {
            throw new InvalidOperationException(
                    "No available seats left on Trip with ID " +
                            tripId
            );
        }

        // Map DTO payload to internal Booking entity
        Booking booking = BookingMapper.mapToEntity(
                bookingRequestDTO,
                trip
        );

        // Save booking and transform entity to response DTO
        Booking savedBooking = bookingRepo.save(booking);
        return BookingMapper.mapToResponseDTO(savedBooking);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<BookingResponseDTO> getBookingPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepo.findAll(pageable)
                .map(BookingMapper::mapToResponseDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookingResponseDTO> getBookingsByCustomer(
            Integer customerId
    ) {
        return paymentRepo.findByCustomerId(customerId)
                .stream()
                .map(Payment::getBooking)
                .map(BookingMapper::mapToResponseDTO)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookingResponseDTO getBookingById(
            Integer bookingId
    ) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking with ID " + bookingId + " not found"));
        return BookingMapper.mapToResponseDTO(booking);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public String cancelBooking(
            Integer bookingId
    ) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking with ID " + bookingId + " not found"));

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

        // Cascade delete: purge associated payment if it exists
        paymentRepo.findByBookingId(bookingId).ifPresent(paymentRepo::delete);

        // Permanently remove booking record
        bookingRepo.delete(booking);

        return bookingDetails;
    }
}