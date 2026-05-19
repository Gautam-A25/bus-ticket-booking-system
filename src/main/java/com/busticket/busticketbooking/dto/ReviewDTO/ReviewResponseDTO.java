package com.busticket.busticketbooking.dto.ReviewDTO;

import java.time.LocalDateTime;

public class ReviewResponseDTO {
    private Integer reviewId;
    private Integer customerId;
    private Integer tripId;
    private Integer rating;
    private String comment;
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
