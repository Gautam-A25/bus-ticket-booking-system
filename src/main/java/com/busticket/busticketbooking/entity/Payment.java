package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// JPA entity representing the 'payments' table in the database
@Entity
@Table(name = "payments")
public class Payment {

    // Auto-generated primary key for each payment record
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer id;

    // Many payments can belong to one booking (Many-to-One relationship)
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    // Many payments can be linked to one customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Payment amount; must be positive and up to 8 integer digits with 2 decimal places
    @Positive(message = "Amount must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Amount must have up to 8 integer digits and 2 decimal places")
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    // Timestamp of when the payment was made; cannot be a future date
    @PastOrPresent(message = "Payment date cannot be in the future")
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    // Stores the payment result — stored as a string ('Success' or 'Failed') in the DB
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

    // Enum representing possible payment outcomes
    public enum PaymentStatus {
        Success, Failed
    }

    // Equality is based on payment ID only (safe for JPA proxies)
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