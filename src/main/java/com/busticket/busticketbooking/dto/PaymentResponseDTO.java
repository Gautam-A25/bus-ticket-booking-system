package com.busticket.busticketbooking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponseDTO {
    private Integer paymentId;
    private Integer bookingId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String paymentStatus;

    public PaymentResponseDTO() {
    }

    public PaymentResponseDTO(Integer paymentId, Integer bookingId, BigDecimal amount, LocalDateTime paymentDate, String paymentStatus) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
