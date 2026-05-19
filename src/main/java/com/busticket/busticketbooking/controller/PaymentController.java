package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.PaymentDTO.PaymentRequestDTO;
import com.busticket.busticketbooking.dto.PaymentDTO.PaymentResponseDTO;
import com.busticket.busticketbooking.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponseDTO> makePayment(@RequestBody PaymentRequestDTO requestDTO) {
        return ResponseEntity.ok(paymentService.makePayment(requestDTO));
    }

    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentDetails(@PathVariable Integer paymentId) {
        return paymentService.getPaymentDetails(paymentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customers/{customerId}/payments")
    public ResponseEntity<List<PaymentResponseDTO>> getCustomerPaymentHistory(@PathVariable Integer customerId) {
        return ResponseEntity.ok(paymentService.getCustomerPaymentHistory(customerId));
    }

    @GetMapping("/bookings/{bookingId}/payment")
    public ResponseEntity<PaymentResponseDTO> getBookingPaymentInfo(@PathVariable Integer bookingId) {
        return paymentService.getBookingPaymentInfo(bookingId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
