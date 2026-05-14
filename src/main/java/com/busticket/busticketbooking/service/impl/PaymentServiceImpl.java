package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.entity.Payment;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;

    public PaymentServiceImpl(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @Override
    public Payment makePayment(Payment payment) {
        return paymentRepo.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentDetails(Integer paymentId) {
        return paymentRepo.findById(paymentId);
    }

    @Override
    public List<Payment> getCustomerPaymentHistory(Integer customerId) {
        return paymentRepo.findByCustomerId(customerId);
    }

    @Override
    public Optional<Payment> getBookingPaymentInfo(Integer bookingId) {
        return paymentRepo.findByBookingId(bookingId);
    }

    @Override
    public Payment updatePaymentStatus(Integer paymentId, String status) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setPaymentStatus(Payment.PaymentStatus.valueOf(status));
        return paymentRepo.save(payment);
    }
}
