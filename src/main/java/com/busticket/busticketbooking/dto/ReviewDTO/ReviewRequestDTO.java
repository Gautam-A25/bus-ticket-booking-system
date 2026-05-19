package com.busticket.busticketbooking.dto;

public class ReviewRequestDTO {
    private Integer customerId;
    private Integer rating;
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
