// Total tests: 9
package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.BookingDTO.BookingRequestDTO;
import com.busticket.busticketbooking.dto.BookingDTO.BookingResponseDTO;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Payment;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.exception.DuplicateResourceException;
import com.busticket.busticketbooking.exception.InvalidOperationException;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private TripRepo tripRepo;

    @Mock
    private PaymentRepo paymentRepo;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private BookingRequestDTO requestDto;
    private Trip trip;
    private Booking booking;
    private Payment payment;

    @BeforeEach
    public void setUp() {
        requestDto = new BookingRequestDTO();
        requestDto.setSeatNumber(15);
        requestDto.setStatus(Booking.BookingStatus.Booked);

        trip = new Trip();
        trip.setId(11);
        trip.setAvailableSeats(30);

        booking = new Booking();
        booking.setId(171);
        booking.setTrip(trip);
        booking.setSeatNumber(15);
        booking.setStatus(Booking.BookingStatus.Booked);

        payment = new Payment();
        payment.setId(100);
        payment.setBooking(booking);
    }

    /**
     * 1. testCreateBooking_Success - Verify that a booking is created successfully when valid details are supplied.
     */
    @Test
    public void testCreateBooking_Success() {
        when(tripRepo.findById(11)).thenReturn(Optional.of(trip));
        when(bookingRepo.existsByTripIdAndSeatNumber(11, 15)).thenReturn(false);
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        BookingResponseDTO response = bookingService.createBooking(11, requestDto);

        assertNotNull(response);
        assertEquals(171, response.getId());
        assertEquals(15, response.getSeatNumber());
    }

    /**
     * 2. testCreateBooking_TripNotFound_ThrowsException - Verify missing Trip ID throws ResourceNotFoundException.
     */
    @Test
    public void testCreateBooking_TripNotFound_ThrowsException() {
        when(tripRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookingService.createBooking(999, requestDto));
    }

    /**
     * 3. testCreateBooking_DuplicateSeat_ThrowsException - Verify duplicate seat booking throws DuplicateResourceException.
     */
    @Test
    public void testCreateBooking_DuplicateSeat_ThrowsException() {
        when(tripRepo.findById(11)).thenReturn(Optional.of(trip));
        when(bookingRepo.existsByTripIdAndSeatNumber(11, 15)).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> bookingService.createBooking(11, requestDto));
    }

    /**
     * 4. testCreateBooking_NoSeatsAvailable_ThrowsException - Verify booking fails when trip available seats <= 0.
     */
    @Test
    public void testCreateBooking_NoSeatsAvailable_ThrowsException() {
        trip.setAvailableSeats(0);
        when(tripRepo.findById(11)).thenReturn(Optional.of(trip));

        assertThrows(InvalidOperationException.class, () -> bookingService.createBooking(11, requestDto));
    }

    /**
     * 5. testGetBookingById_Success - Verify booking retrieval by ID.
     */
    @Test
    public void testGetBookingById_Success() {
        when(bookingRepo.findById(171)).thenReturn(Optional.of(booking));
        BookingResponseDTO response = bookingService.getBookingById(171);
        assertNotNull(response);
        assertEquals(171, response.getId());
    }

    /**
     * 6. testGetBookingById_NotFound_ThrowsException - Verify missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testGetBookingById_NotFound_ThrowsException() {
        when(bookingRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookingService.getBookingById(999));
    }

    /**
     * 7. testGetBookingsByCustomer_Success - Verify listing bookings by customer ID.
     */
    @Test
    public void testGetBookingsByCustomer_Success() {
        when(paymentRepo.findByCustomerId(2)).thenReturn(Arrays.asList(payment));
        List<BookingResponseDTO> response = bookingService.getBookingsByCustomer(2);
        assertEquals(1, response.size());
    }

    /**
     * 8. testCancelBooking_Success - Verify that deleting a booking returns success message.
     */
    @Test
    public void testCancelBooking_Success() {
        when(bookingRepo.findById(171)).thenReturn(Optional.of(booking));
        doNothing().when(bookingRepo).delete(booking);
        String response = bookingService.cancelBooking(171);
        assertEquals(
                "Booking Cancelled Successfully : " +
                        "ID = 171" +
                        ", Trip ID = 11" +
                        ", Seat Number = 15" +
                        ", Status = Booked",
                response
        );
    }

    /**
     * 9. testCancelBooking_NotFound_ThrowsException - Verify cancelling missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testCancelBooking_NotFound_ThrowsException() {
        when(bookingRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookingService.cancelBooking(999));
    }
}
