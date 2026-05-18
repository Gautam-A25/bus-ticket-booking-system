package com.busticket.busticketbooking.dto;

import java.math.BigDecimal;

public class PaymentRequestDTO {
    private Integer bookingId;
    private Integer customerId;
    private BigDecimal amount;
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
