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

/**
 * Concrete implementation of {@link PaymentService}.
 *
 * <p>Handles processing of payments for bookings, retrieving transaction histories,
 * checking payment details, and updating status fields. Includes safety verification
 * rules to trigger custom exceptions if payments fail or are declined.</p>
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    /** Repository for payment database operations. */
    private final PaymentRepo paymentRepo;

    /** Repository for booking database operations. */
    private final BookingRepo bookingRepo;

    /** Repository for customer database operations. */
    private final CustomerRepo customerRepo;

    /**
     * Constructs a PaymentServiceImpl with required repository dependencies.
     *
     * @param paymentRepo  repository for payment data access
     * @param bookingRepo  repository for booking data access
     * @param customerRepo repository for customer data access
     */
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

        // Build a new Payment entity and populate its fields
        Payment payment = new Payment();

        payment.setBooking(booking);

        payment.setCustomer(customer);

        payment.setAmount(requestDTO.getAmount());

        payment.setPaymentDate(LocalDateTime.now());  // Record the current timestamp

        payment.setPaymentStatus(
                parsePaymentStatus(
                        requestDTO.getPaymentStatus()));

        // Save to database
        Payment savedPayment =
                paymentRepo.save(payment);

        // Reject payment after saving to database if the status is already FAILED or DECLINED
        if ("FAILED".equalsIgnoreCase(
                requestDTO.getPaymentStatus())
                ||
                "DECLINED".equalsIgnoreCase(
                        requestDTO.getPaymentStatus())) {

            throw new PaymentFailedException(
                    "Payment processing failed. Transaction status: "
                            + requestDTO.getPaymentStatus());
        }

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

    /** Returns a paginated, newest-first page of all payment records. */
    @Override
    public Page<PaymentResponseDTO> getPaymentPage(int page, int size) {
        return paymentRepo.findAll(
                PageRequest.of(page, size, Sort.by("id").descending())
        ).map(PaymentMapper::mapToResponseDTO);
    }

    /** Permanently deletes a payment by ID; throws {@link com.busticket.busticketbooking.exception.ResourceNotFoundException} if not found. */
    @Override
    public void deletePayment(Integer id) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + id + " not found"));
        paymentRepo.delete(payment);
    }
}