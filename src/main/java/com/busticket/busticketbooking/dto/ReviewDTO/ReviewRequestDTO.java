package com.busticket.busticketbooking.dto.ReviewDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// DTO used to receive review data from the client in a POST request
public class ReviewRequestDTO {
    // ID of the customer submitting the review
    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    // Star rating between 1 (worst) and 5 (best)
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;

    // Optional written comment; max 500 characters
    @Size(max = 500, message = "Comment must not exceed 500 characters")
    private String comment;

    public ReviewRequestDTO() {
    }

    public ReviewRequestDTO(Integer customerId, Integer rating, String comment) {
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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
}
