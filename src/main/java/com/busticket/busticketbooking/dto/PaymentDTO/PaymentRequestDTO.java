package com.busticket.busticketbooking.dto.PaymentDTO;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// DTO used to receive payment data from the client in a POST request
public class PaymentRequestDTO {
    // ID of the booking this payment is for
    @NotNull(message = "Booking ID is required")
    private Integer bookingId;

    // ID of the customer making the payment
    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    // Amount to be charged; must be a positive number
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    // Payment result status, e.g. "SUCCESS", "FAILED", "DECLINED"
    @NotBlank(message = "Payment status is required")
    @Size(max = 20, message = "Payment status must not exceed 20 characters")
    private String paymentStatus;

    public PaymentRequestDTO() {
    }

    public PaymentRequestDTO(Integer bookingId, Integer customerId, BigDecimal amount, String paymentStatus) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
