package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.PaymentDTO.PaymentRequestDTO;
import com.busticket.busticketbooking.dto.PaymentDTO.PaymentResponseDTO;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    PaymentResponseDTO makePayment(PaymentRequestDTO requestDTO);
    Optional<PaymentResponseDTO> getPaymentDetails(Integer paymentId);
    List<PaymentResponseDTO> getCustomerPaymentHistory(Integer customerId);
    Optional<PaymentResponseDTO> getBookingPaymentInfo(Integer bookingId);
    PaymentResponseDTO updatePaymentStatus(Integer paymentId, String status);
}
