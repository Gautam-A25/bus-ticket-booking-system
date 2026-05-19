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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test cases covered:
 * 1. testMakePayment_Success_CaseInsensitive - Verify that a payment is made successfully and status string casing is handled robustly.
 * 2. testMakePayment_FailedStatus_ThrowsPaymentFailedException - Verify that supplying FAILED status triggers PaymentFailedException (HTTP 402).
 * 3. testMakePayment_BookingNotFound_ThrowsResourceNotFoundException - Verify that payment attempts for non-existent bookings trigger ResourceNotFoundException.
 * 4. testGetPaymentDetails_Success - Verify that payment record details are retrieved successfully by ID.
 */
@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepo paymentRepo;

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Booking booking;
    private Customer customer;
    private Payment payment;
    private PaymentRequestDTO successRequest;
    private PaymentRequestDTO failedRequest;

    @BeforeEach
    public void setUp() {
        booking = new Booking();
        booking.setId(1);
        booking.setSeatNumber(12);

        customer = new Customer();
        customer.setId(2);
        customer.setName("Rohan Malhotra");
        customer.setEmail("rohan@gmail.com");

        payment = new Payment();
        payment.setId(10);
        payment.setBooking(booking);
        payment.setCustomer(customer);
        payment.setAmount(new BigDecimal("650.00"));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(Payment.PaymentStatus.Success);

        successRequest = new PaymentRequestDTO(1, 2, new BigDecimal("650.00"), "SUCCESS");
        failedRequest = new PaymentRequestDTO(1, 2, new BigDecimal("650.00"), "FAILED");
    }

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

    @Test
    public void testMakePayment_FailedStatus_ThrowsPaymentFailedException() {
        when(bookingRepo.findById(1)).thenReturn(Optional.of(booking));
        when(customerRepo.findById(2)).thenReturn(Optional.of(customer));

        assertThrows(PaymentFailedException.class, () -> {
            paymentService.makePayment(failedRequest);
        });
        verify(paymentRepo, never()).save(any(Payment.class));
    }

    @Test
    public void testMakePayment_BookingNotFound_ThrowsResourceNotFoundException() {
        when(bookingRepo.findById(99)).thenReturn(Optional.empty());

        PaymentRequestDTO badRequest = new PaymentRequestDTO(99, 2, new BigDecimal("650.00"), "SUCCESS");

        assertThrows(ResourceNotFoundException.class, () -> {
            paymentService.makePayment(badRequest);
        });
        verify(paymentRepo, never()).save(any(Payment.class));
    }

    @Test
    public void testGetPaymentDetails_Success() {
        when(paymentRepo.findById(10)).thenReturn(Optional.of(payment));

        Optional<PaymentResponseDTO> responseOpt = paymentService.getPaymentDetails(10);

        assertTrue(responseOpt.isPresent());
        assertEquals(10, responseOpt.get().getPaymentId());
        assertEquals("Success", responseOpt.get().getPaymentStatus());
    }
}
