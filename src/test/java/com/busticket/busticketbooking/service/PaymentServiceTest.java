// Total tests: 10
package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.PaymentDTO.PaymentRequestDTO;
import com.busticket.busticketbooking.dto.PaymentDTO.PaymentResponseDTO;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.entity.Payment;
import com.busticket.busticketbooking.exception.PaymentFailedException;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Unit tests for PaymentServiceImpl using Mockito (no real database or Spring context)
@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    // Mock repositories — these are fake objects that simulate DB behaviour
    @Mock
    private PaymentRepo paymentRepo;

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private CustomerRepo customerRepo;

    // Inject the mocks into the actual service implementation being tested
    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Booking booking;
    private Customer customer;
    private Payment payment;
    private PaymentRequestDTO successRequest;
    private PaymentRequestDTO failedRequest;
    private PaymentRequestDTO declinedRequest;

    // Set up reusable test data before each test runs
    @BeforeEach
    public void setUp() {
        // A sample booking with seat number 12
        booking = new Booking();
        booking.setId(1);
        booking.setSeatNumber(12);

        // A sample customer
        customer = new Customer();
        customer.setId(2);
        customer.setName("Rohan Malhotra");
        customer.setEmail("rohan@gmail.com");

        // A sample payment entity with SUCCESS status
        payment = new Payment();
        payment.setId(10);
        payment.setBooking(booking);
        payment.setCustomer(customer);
        payment.setAmount(new BigDecimal("650.00"));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(Payment.PaymentStatus.Success);

        // Three request DTOs: one success, one failed, one declined
        successRequest = new PaymentRequestDTO(1, 2, new BigDecimal("650.00"), "SUCCESS");
        failedRequest = new PaymentRequestDTO(1, 2, new BigDecimal("650.00"), "FAILED");
        declinedRequest = new PaymentRequestDTO(1, 2, new BigDecimal("650.00"), "DECLINED");
    }

    /**
     * 1. testMakePayment_Success_CaseInsensitive - Verify that a payment is made successfully and status string casing is handled robustly.
     */
    @Test
    public void testMakePayment_Success_CaseInsensitive() {
        when(bookingRepo.findById(1)).thenReturn(Optional.of(booking));
        when(customerRepo.findById(2)).thenReturn(Optional.of(customer));
        when(paymentRepo.save(any(Payment.class))).thenReturn(payment);

        PaymentResponseDTO response = paymentService.makePayment(successRequest);

        assertNotNull(response);
        assertEquals(10, response.getPaymentId());
        assertEquals("Success", response.getPaymentStatus());
        verify(paymentRepo, times(1)).save(any(Payment.class));
    }

    /**
     * 2. testMakePayment_FailedStatus_ThrowsPaymentFailedException - Verify that supplying FAILED status triggers PaymentFailedException (HTTP 402).
     */
    @Test
    public void testMakePayment_FailedStatus_ThrowsPaymentFailedException() {
        when(bookingRepo.findById(1)).thenReturn(Optional.of(booking));
        when(customerRepo.findById(2)).thenReturn(Optional.of(customer));

        assertThrows(PaymentFailedException.class, () -> {
            paymentService.makePayment(failedRequest);
        });
        verify(paymentRepo, never()).save(any(Payment.class));
    }

    /**
     * 3. testMakePayment_DeclinedStatus_ThrowsPaymentFailedException - Verify that supplying DECLINED status triggers PaymentFailedException.
     */
    @Test
    public void testMakePayment_DeclinedStatus_ThrowsPaymentFailedException() {
        when(bookingRepo.findById(1)).thenReturn(Optional.of(booking));
        when(customerRepo.findById(2)).thenReturn(Optional.of(customer));

        assertThrows(PaymentFailedException.class, () -> {
            paymentService.makePayment(declinedRequest);
        });
        verify(paymentRepo, never()).save(any(Payment.class));
    }

    /**
     * 4. testMakePayment_BookingNotFound_ThrowsResourceNotFoundException - Verify that payment attempts for non-existent bookings trigger ResourceNotFoundException.
     */
    @Test
    public void testMakePayment_BookingNotFound_ThrowsResourceNotFoundException() {
        when(bookingRepo.findById(99)).thenReturn(Optional.empty());

        PaymentRequestDTO badRequest = new PaymentRequestDTO(99, 2, new BigDecimal("650.00"), "SUCCESS");

        assertThrows(ResourceNotFoundException.class, () -> {
            paymentService.makePayment(badRequest);
        });
        verify(paymentRepo, never()).save(any(Payment.class));
    }

    /**
     * 5. testMakePayment_CustomerNotFound_ThrowsResourceNotFoundException - Verify that payment attempts for non-existent customers trigger ResourceNotFoundException.
     */
    @Test
    public void testMakePayment_CustomerNotFound_ThrowsResourceNotFoundException() {
        when(bookingRepo.findById(1)).thenReturn(Optional.of(booking));
        when(customerRepo.findById(99)).thenReturn(Optional.empty());

        PaymentRequestDTO badRequest = new PaymentRequestDTO(1, 99, new BigDecimal("650.00"), "SUCCESS");

        assertThrows(ResourceNotFoundException.class, () -> {
            paymentService.makePayment(badRequest);
        });
        verify(paymentRepo, never()).save(any(Payment.class));
    }

    /**
     * 6. testGetPaymentDetails_Success - Verify that payment record details are retrieved successfully by ID.
     */
    @Test
    public void testGetPaymentDetails_Success() {
        when(paymentRepo.findById(10)).thenReturn(Optional.of(payment));

        Optional<PaymentResponseDTO> responseOpt = paymentService.getPaymentDetails(10);

        assertTrue(responseOpt.isPresent());
        assertEquals(10, responseOpt.get().getPaymentId());
        assertEquals("Success", responseOpt.get().getPaymentStatus());
    }

    /**
     * 7. testGetPaymentDetails_NotFound_ReturnsEmpty - Verify that retrieving details of a missing payment returns empty Optional.
     */
    @Test
    public void testGetPaymentDetails_NotFound_ReturnsEmpty() {
        when(paymentRepo.findById(999)).thenReturn(Optional.empty());

        Optional<PaymentResponseDTO> responseOpt = paymentService.getPaymentDetails(999);

        assertFalse(responseOpt.isPresent());
    }

    /**
     * 8. testGetCustomerPaymentHistory_Success - Verify that payment history filtered by customer ID is returned successfully.
     */
    @Test
    public void testGetCustomerPaymentHistory_Success() {
        when(paymentRepo.findByCustomerId(2)).thenReturn(Arrays.asList(payment));

        List<PaymentResponseDTO> history = paymentService.getCustomerPaymentHistory(2);

        assertNotNull(history);
        assertEquals(1, history.size());
        assertEquals(10, history.get(0).getPaymentId());
    }

    /**
     * 9. testGetBookingPaymentInfo_Success - Verify that payment info filtered by booking ID is returned successfully.
     */
    @Test
    public void testGetBookingPaymentInfo_Success() {
        when(paymentRepo.findByBookingId(1)).thenReturn(Optional.of(payment));

        Optional<PaymentResponseDTO> responseOpt = paymentService.getBookingPaymentInfo(1);

        assertTrue(responseOpt.isPresent());
        assertEquals(10, responseOpt.get().getPaymentId());
    }

    /**
     * 10. testUpdatePaymentStatus_Success - Verify that payment status can be successfully updated.
     */
    @Test
    public void testUpdatePaymentStatus_Success() {
        when(paymentRepo.findById(10)).thenReturn(Optional.of(payment));
        when(paymentRepo.save(any(Payment.class))).thenReturn(payment);

        PaymentResponseDTO response = paymentService.updatePaymentStatus(10, "SUCCESS");

        assertNotNull(response);
        assertEquals(10, response.getPaymentId());
        verify(paymentRepo, times(1)).save(payment);
    }
}
