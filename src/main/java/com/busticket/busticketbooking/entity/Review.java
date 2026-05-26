package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

// JPA entity representing the 'reviews' table in the database
@Entity
@Table(name = "reviews")
public class Review {

    // Primary key — manually assigned (no auto-generation for reviews)
    @Id
    @Column(name = "review_id")
    private Integer id;

    // Many reviews can be linked to one customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Many reviews can be linked to one trip
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    // Star rating from 1 to 5; validated at both DTO and entity level
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not be greater than 5")
    @Column(nullable = false)
    private Integer rating;

    // Optional text comment; stored as TEXT in DB to support long reviews
    @Size(max = 5000, message = "Comment must not exceed 5000 characters")
    @Column(columnDefinition = "TEXT")
    private String comment;

    // Date and time when the review was submitted; cannot be a future date
    @PastOrPresent(message = "Review date cannot be in the future")
    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    /** Default constructor required by JPA/Hibernate. */
    public Review() {
    }

    /** Parameterized constructor for building a Review with all fields set. */
    public Review(Integer id, Customer customer, Trip trip, Integer rating, String comment, LocalDateTime reviewDate) {
        this.id = id;
        this.customer = customer;
        this.trip = trip;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    // Equality based on review ID only
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id != null && id.equals(review.id);
    }

    /** Generates a stable hash code based on entity class — consistent with JPA proxy safety. */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /** Returns a human-readable string of the review; useful for logging and debugging. */
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", customerId=" + (customer != null ? customer.getId() : null) +
                ", tripId=" + (trip != null ? trip.getId() : null) +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}