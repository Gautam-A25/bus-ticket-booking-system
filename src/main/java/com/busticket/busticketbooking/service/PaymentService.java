package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.PaymentDTO.PaymentRequestDTO;
import com.busticket.busticketbooking.dto.PaymentDTO.PaymentResponseDTO;
import java.util.List;
import java.util.Optional;

// Service interface defining all payment business operations
public interface PaymentService {
    // Process and save a new payment
    PaymentResponseDTO makePayment(PaymentRequestDTO requestDTO);
    // Fetch payment details by payment ID
    Optional<PaymentResponseDTO> getPaymentDetails(Integer paymentId);
    // Get all payments made by a customer
    List<PaymentResponseDTO> getCustomerPaymentHistory(Integer customerId);
    // Get the payment associated with a specific booking
    Optional<PaymentResponseDTO> getBookingPaymentInfo(Integer bookingId);
    // Update the status (Success/Failed) of an existing payment
    PaymentResponseDTO updatePaymentStatus(Integer paymentId, String status);
}
