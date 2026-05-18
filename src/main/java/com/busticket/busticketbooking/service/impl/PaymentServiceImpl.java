package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.PaymentRequestDTO;
import com.busticket.busticketbooking.dto.PaymentResponseDTO;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.entity.Payment;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.service.PaymentService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;

    public PaymentServiceImpl(PaymentRepo paymentRepo, BookingRepo bookingRepo, CustomerRepo customerRepo) {
        this.paymentRepo = paymentRepo;
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public PaymentResponseDTO makePayment(PaymentRequestDTO requestDTO) {
        Booking booking = bookingRepo.findById(requestDTO.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        Customer customer = customerRepo.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setCustomer(customer);
        payment.setAmount(requestDTO.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(Payment.PaymentStatus.valueOf(requestDTO.getPaymentStatus()));

        Payment savedPayment = paymentRepo.save(payment);
        return mapToResponseDTO(savedPayment);
    }

    @Override
    public Optional<PaymentResponseDTO> getPaymentDetails(Integer paymentId) {
        return paymentRepo.findById(paymentId).map(this::mapToResponseDTO);
    }

    @Override
    public List<PaymentResponseDTO> getCustomerPaymentHistory(Integer customerId) {
        return paymentRepo.findByCustomerId(customerId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentResponseDTO> getBookingPaymentInfo(Integer bookingId) {
        return paymentRepo.findByBookingId(bookingId).map(this::mapToResponseDTO);
    }

    @Override
    public PaymentResponseDTO updatePaymentStatus(Integer paymentId, String status) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        payment.setPaymentStatus(Payment.PaymentStatus.valueOf(status));
        return mapToResponseDTO(paymentRepo.save(payment));
    }

    private PaymentResponseDTO mapToResponseDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getBooking().getId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentStatus().name()
        );
    }
}
