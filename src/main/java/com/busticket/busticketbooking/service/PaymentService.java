package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.PaymentDTO.PaymentRequestDTO;
import com.busticket.busticketbooking.dto.PaymentDTO.PaymentResponseDTO;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

/**
 * Service interface defining all payment business operations.
 *
 * <p>A {@code Payment} record represents the transaction details for a particular {@code Booking}.
 * It stores the paid amount, payment status (Success or Failed), timestamp, and links the customer.
 * Deleting a payment breaks the link without canceling the booking, while deleting a booking will
 * cascade-delete its payment.</p>
 */
public interface PaymentService {
    /**
     * Processes a new payment for a booking.
     *
     * @param requestDTO payment details including booking ID, customer ID, amount, and status
     * @return a {@link PaymentResponseDTO} representing the created payment record
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the booking or customer is not found
     * @throws com.busticket.busticketbooking.exception.PaymentFailedException if the payment status is Failed or Declined
     */
    PaymentResponseDTO makePayment(PaymentRequestDTO requestDTO);

    /**
     * Retrieves payment details by its ID.
     *
     * @param paymentId ID of the payment to retrieve
     * @return an {@link Optional} containing the payment response DTO if found, or empty otherwise
     */
    Optional<PaymentResponseDTO> getPaymentDetails(Integer paymentId);

    /**
     * Retrieves all payments made by a specific customer, ordered by creation.
     *
     * @param customerId ID of the customer
     * @return a list of payment response DTOs for the customer
     */
    List<PaymentResponseDTO> getCustomerPaymentHistory(Integer customerId);

    /**
     * Retrieves the payment linked to a specific booking.
     *
     * @param bookingId ID of the booking
     * @return an {@link Optional} containing the payment response DTO if found, or empty otherwise
     */
    Optional<PaymentResponseDTO> getBookingPaymentInfo(Integer bookingId);

    /**
     * Updates the status (Success/Failed) of an existing payment.
     *
     * @param paymentId ID of the payment to update
     * @param status    the new status string (e.g., "SUCCESS" or "FAILED")
     * @return the updated payment response DTO
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the payment is not found
     */
    PaymentResponseDTO updatePaymentStatus(Integer paymentId, String status);

    /**
     * Retrieves a paginated, ID-descending (newest first) slice of all payments.
     *
     * @param page zero-based page index
     * @param size maximum number of records per page
     * @return a {@link Page} of payment response DTOs
     */
    Page<PaymentResponseDTO> getPaymentPage(int page, int size);

    /**
     * Permanently deletes a payment record.
     *
     * @param id ID of the payment to delete
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the payment is not found
     */
    void deletePayment(Integer id);
}
