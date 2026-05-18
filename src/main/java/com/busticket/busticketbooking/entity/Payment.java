package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Positive(message = "Amount must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Amount must have up to 8 integer digits and 2 decimal places")
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @PastOrPresent(message = "Payment date cannot be in the future")
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", columnDefinition = "ENUM('Success', 'Failed')")
    private PaymentStatus paymentStatus;

    public Payment() {
    }

    public Payment(Integer id, Booking booking, Customer customer, BigDecimal amount, LocalDateTime paymentDate, PaymentStatus paymentStatus) {
        this.id = id;
        this.booking = booking;
        this.customer = customer;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public enum PaymentStatus {
        Success, Failed
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id != null && id.equals(payment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", bookingId=" + (booking != null ? booking.getId() : null) +
                ", customerId=" + (customer != null ? customer.getId() : null) +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}