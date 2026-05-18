package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @Column(name = "review_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not be greater than 5")
    @Column(nullable = false)
    private Integer rating;

    @Size(max = 5000, message = "Comment must not exceed 5000 characters")
    @Column(columnDefinition = "TEXT")
    private String comment;

    @PastOrPresent(message = "Review date cannot be in the future")
    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    public Review() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id != null && id.equals(review.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

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