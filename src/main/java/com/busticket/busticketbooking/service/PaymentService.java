package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.entity.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment makePayment(Payment payment);
    Optional<Payment> getPaymentDetails(Integer paymentId);
    List<Payment> getCustomerPaymentHistory(Integer customerId);
    Optional<Payment> getBookingPaymentInfo(Integer bookingId);
    Payment updatePaymentStatus(Integer paymentId, String status);
}
