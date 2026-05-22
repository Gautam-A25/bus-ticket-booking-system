package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.PaymentDTO.PaymentRequestDTO;
import com.busticket.busticketbooking.dto.PaymentDTO.PaymentResponseDTO;

import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.entity.Payment;

import com.busticket.busticketbooking.exception.PaymentFailedException;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;

import com.busticket.busticketbooking.mapper.PaymentMapper;

import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;

import com.busticket.busticketbooking.service.PaymentService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Concrete implementation of PaymentService; handles all payment business logic
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;

    private final BookingRepo bookingRepo;

    private final CustomerRepo customerRepo;

    // Constructor injection — Spring injects all three repositories automatically
    public PaymentServiceImpl(PaymentRepo paymentRepo,
                              BookingRepo bookingRepo,
                              CustomerRepo customerRepo) {

        this.paymentRepo = paymentRepo;
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public PaymentResponseDTO makePayment(
            PaymentRequestDTO requestDTO) {

        // Verify the booking exists; throw 404 if not found
        Booking booking = bookingRepo.findById(
                        requestDTO.getBookingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking with ID "
                                        + requestDTO.getBookingId()
                                        + " not found"));

        // Verify the customer exists; throw 404 if not found
        Customer customer = customerRepo.findById(
                        requestDTO.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer with ID "
                                        + requestDTO.getCustomerId()
                                        + " not found"));

        // Reject payment early if the status is already FAILED or DECLINED
        if ("FAILED".equalsIgnoreCase(
                requestDTO.getPaymentStatus())
                ||
                "DECLINED".equalsIgnoreCase(
                        requestDTO.getPaymentStatus())) {

            throw new PaymentFailedException(
                    "Payment processing failed. Transaction status: "
                            + requestDTO.getPaymentStatus());
        }

        // Build a new Payment entity and populate its fields
        Payment payment = new Payment();

        payment.setBooking(booking);

        payment.setCustomer(customer);

        payment.setAmount(requestDTO.getAmount());

        payment.setPaymentDate(LocalDateTime.now());  // Record the current timestamp

        payment.setPaymentStatus(
                parsePaymentStatus(
                        requestDTO.getPaymentStatus()));

        // Save to database and return as a response DTO
        Payment savedPayment =
                paymentRepo.save(payment);

        return PaymentMapper.mapToResponseDTO(
                savedPayment);
    }

    @Override
    public Optional<PaymentResponseDTO> getPaymentDetails(
            Integer paymentId) {

        // Look up payment by ID and map to response DTO if found
        return paymentRepo.findById(paymentId)
                .map(PaymentMapper::mapToResponseDTO);
    }

    @Override
    public List<PaymentResponseDTO>
    getCustomerPaymentHistory(Integer customerId) {

        // Fetch all payments by customer ID and convert each to a response DTO
        return paymentRepo.findByCustomerId(customerId)
                .stream()
                .map(PaymentMapper::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentResponseDTO>
    getBookingPaymentInfo(Integer bookingId) {

        // Fetch the payment linked to this booking and map it if present
        return paymentRepo.findByBookingId(bookingId)
                .map(PaymentMapper::mapToResponseDTO);
    }

    @Override
    public PaymentResponseDTO updatePaymentStatus(
            Integer paymentId,
            String status) {

        // Fetch existing payment; throw 404 if not found
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment with ID "
                                        + paymentId
                                        + " not found"));

        // Update the status field using the helper method
        payment.setPaymentStatus(
                parsePaymentStatus(status));

        // Save the updated payment and return the response DTO
        Payment updatedPayment =
                paymentRepo.save(payment);

        return PaymentMapper.mapToResponseDTO(
                updatedPayment);
    }

    // Helper method: converts a status string into the PaymentStatus enum
    // Defaults to Failed if the string is null or unrecognized
    private Payment.PaymentStatus parsePaymentStatus(
            String status) {

        if (status == null) {
            // Null status defaults to Failed
            return Payment.PaymentStatus.Failed;
        }

        if ("SUCCESS".equalsIgnoreCase(status)) {

            return Payment.PaymentStatus.Success;
        }

        if ("FAILED".equalsIgnoreCase(status)
                ||
                "DECLINED".equalsIgnoreCase(status)) {

            return Payment.PaymentStatus.Failed;
        }

        // Any other unrecognized value is treated as Failed
        return Payment.PaymentStatus.Failed;
    }

    @Override
    public Page<PaymentResponseDTO> getPaymentPage(int page, int size) {
        return paymentRepo.findAll(
                PageRequest.of(page, size, Sort.by("id").descending())
        ).map(PaymentMapper::mapToResponseDTO);
    }
}