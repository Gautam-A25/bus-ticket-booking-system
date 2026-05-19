package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.PaymentDTO.PaymentRequestDTO;
import com.busticket.busticketbooking.dto.PaymentDTO.PaymentResponseDTO;
import com.busticket.busticketbooking.service.PaymentService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponseDTO> makePayment(@Valid @RequestBody PaymentRequestDTO requestDTO) {
        return ResponseEntity.ok(paymentService.makePayment(requestDTO));
    }

    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentDetails(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentDetails(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + paymentId + " not found")));
    }

    @GetMapping("/customers/{customerId}/payments")
    public ResponseEntity<List<PaymentResponseDTO>> getCustomerPaymentHistory(@PathVariable Integer customerId) {
        return ResponseEntity.ok(paymentService.getCustomerPaymentHistory(customerId));
    }

    @GetMapping("/bookings/{bookingId}/payment")
    public ResponseEntity<PaymentResponseDTO> getBookingPaymentInfo(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(paymentService.getBookingPaymentInfo(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("No payment found for Booking ID " + bookingId)));
    }

    @PatchMapping("/payments/{paymentId}/status")
    public ResponseEntity<PaymentResponseDTO> updatePaymentStatus(@PathVariable Integer paymentId, @RequestParam String status) {
        try {
            return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, status));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
