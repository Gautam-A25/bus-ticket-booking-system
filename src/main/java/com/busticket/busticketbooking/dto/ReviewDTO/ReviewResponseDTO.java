package com.busticket.busticketbooking.dto.ReviewDTO;

import java.time.LocalDateTime;

// DTO used to send review details back to the client in an API response
public class ReviewResponseDTO {
    // Unique ID of the review record
    private Integer reviewId;
    // ID of the customer who wrote the review
    private Integer customerId;
    // ID of the trip being reviewed
    private Integer tripId;
    // Star rating (1–5)
    private Integer rating;
    // Written comment from the customer
    private String comment;
    // Date and time the review was submitted
    private LocalDateTime reviewDate;

    public ReviewResponseDTO() {
    }

    public ReviewResponseDTO(Integer reviewId, Integer customerId, Integer tripId, Integer rating, String comment, LocalDateTime reviewDate) {
        this.reviewId = reviewId;
        this.customerId = customerId;
        this.tripId = tripId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
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
}
