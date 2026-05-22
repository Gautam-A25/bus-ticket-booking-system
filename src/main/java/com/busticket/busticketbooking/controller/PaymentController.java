package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.PaymentDTO.PaymentRequestDTO;
import com.busticket.busticketbooking.dto.PaymentDTO.PaymentResponseDTO;
import com.busticket.busticketbooking.service.PaymentService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller for all payment-related API endpoints
@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    // Service layer injected via constructor (no field injection)
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // POST /api/v1/payments — creates a new payment record for a booking
    @PostMapping("/payments")
    public ResponseEntity<PaymentResponseDTO> makePayment(@Valid @RequestBody PaymentRequestDTO requestDTO) {
        return ResponseEntity.ok(paymentService.makePayment(requestDTO));
    }

    // GET /api/v1/payments/{paymentId} — fetch a single payment by its ID; throws 404 if not found
    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentDetails(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentDetails(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + paymentId + " not found")));
    }

    // GET /api/v1/customers/{customerId}/payments — returns all payments made by a specific customer
    @GetMapping("/customers/{customerId}/payments")
    public ResponseEntity<List<PaymentResponseDTO>> getCustomerPaymentHistory(@PathVariable Integer customerId) {
        return ResponseEntity.ok(paymentService.getCustomerPaymentHistory(customerId));
    }

    // GET /api/v1/bookings/{bookingId}/payment — returns the payment linked to a specific booking
    @GetMapping("/bookings/{bookingId}/payment")
    public ResponseEntity<PaymentResponseDTO> getBookingPaymentInfo(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(paymentService.getBookingPaymentInfo(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("No payment found for Booking ID " + bookingId)));
    }

    // PATCH /api/v1/payments/{paymentId}/status — updates the status (Success/Failed) of an existing payment
    @PatchMapping("/payments/{paymentId}/status")
    public ResponseEntity<PaymentResponseDTO> updatePaymentStatus(@PathVariable Integer paymentId, @RequestParam String status) {
        try {
            return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, status));
        } catch (Exception e) {
            // Return 404 if payment ID is not found
            return ResponseEntity.notFound().build();
        }
    }
}
