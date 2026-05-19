package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.PaymentDTO.PaymentRequestDTO;
import com.busticket.busticketbooking.dto.PaymentDTO.PaymentResponseDTO;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.entity.Payment;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.service.PaymentService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.exception.PaymentFailedException;
import com.busticket.busticketbooking.mapper.PaymentMapper;
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
                .orElseThrow(() -> new ResourceNotFoundException("Booking with ID " + requestDTO.getBookingId() + " not found"));
        Customer customer = customerRepo.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID " + requestDTO.getCustomerId() + " not found"));

        if ("FAILED".equalsIgnoreCase(requestDTO.getPaymentStatus()) || "DECLINED".equalsIgnoreCase(requestDTO.getPaymentStatus())) {
            throw new PaymentFailedException("Payment processing failed. Transaction status: " + requestDTO.getPaymentStatus());
        }

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setCustomer(customer);
        payment.setAmount(requestDTO.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        
        Payment.PaymentStatus status;
        if ("SUCCESS".equalsIgnoreCase(requestDTO.getPaymentStatus())) {
            status = Payment.PaymentStatus.Success;
        } else if ("FAILED".equalsIgnoreCase(requestDTO.getPaymentStatus())) {
            status = Payment.PaymentStatus.Failed;
        } else {
            try {
                status = Payment.PaymentStatus.valueOf(requestDTO.getPaymentStatus());
            } catch (IllegalArgumentException e) {
                status = Payment.PaymentStatus.Failed;
            }
        }
        payment.setPaymentStatus(status);

        Payment savedPayment = paymentRepo.save(payment);
        return PaymentMapper.mapToResponseDTO(savedPayment);
    }

    @Override
    public Optional<PaymentResponseDTO> getPaymentDetails(Integer paymentId) {
        return paymentRepo.findById(paymentId).map(PaymentMapper::mapToResponseDTO);
    }

    @Override
    public List<PaymentResponseDTO> getCustomerPaymentHistory(Integer customerId) {
        return paymentRepo.findByCustomerId(customerId).stream()
                .map(PaymentMapper::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentResponseDTO> getBookingPaymentInfo(Integer bookingId) {
        return paymentRepo.findByBookingId(bookingId).map(PaymentMapper::mapToResponseDTO);
    }

    @Override
    public PaymentResponseDTO updatePaymentStatus(Integer paymentId, String status) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + paymentId + " not found"));
        
        Payment.PaymentStatus parsedStatus;
        if ("SUCCESS".equalsIgnoreCase(status)) {
            parsedStatus = Payment.PaymentStatus.Success;
        } else if ("FAILED".equalsIgnoreCase(status)) {
            parsedStatus = Payment.PaymentStatus.Failed;
        } else {
            try {
                parsedStatus = Payment.PaymentStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                parsedStatus = Payment.PaymentStatus.Failed;
            }
        }
        payment.setPaymentStatus(parsedStatus);
        return PaymentMapper.mapToResponseDTO(paymentRepo.save(payment));
    }
}
