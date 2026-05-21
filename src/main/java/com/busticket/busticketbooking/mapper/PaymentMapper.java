package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.PaymentDTO.PaymentRequestDTO;
import com.busticket.busticketbooking.dto.PaymentDTO.PaymentResponseDTO;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.entity.Payment;

import java.time.LocalDateTime;

// Utility class to convert between Payment entity and its DTOs
public class PaymentMapper {

    // Converts a PaymentRequestDTO into a Payment entity ready to save to the database
    public static Payment mapToEntity(
            PaymentRequestDTO requestDTO,
            Booking booking,
            Customer customer
    ) {
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setCustomer(customer);
        payment.setAmount(requestDTO.getAmount());
        payment.setPaymentDate(LocalDateTime.now());  // Set current time as payment date
        payment.setPaymentStatus(Payment.PaymentStatus.valueOf(requestDTO.getPaymentStatus()));
        return payment;
    }

    // Converts a Payment entity into a PaymentResponseDTO to send back in the API response
    public static PaymentResponseDTO mapToResponseDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getBooking().getId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentStatus().name()
        );
    }
}
