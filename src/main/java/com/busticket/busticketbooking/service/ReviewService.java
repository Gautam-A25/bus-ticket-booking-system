package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.ReviewDTO.ReviewRequestDTO;
import com.busticket.busticketbooking.dto.ReviewDTO.ReviewResponseDTO;
import java.util.List;

// Service interface defining all review business operations
public interface ReviewService {
    // Save a new review for a specific trip
    ReviewResponseDTO submitReview(Integer tripId, ReviewRequestDTO requestDTO);
    // Get all reviews for a specific trip
    List<ReviewResponseDTO> getTripReviews(Integer tripId);
    // Get all reviews written by a specific customer
    List<ReviewResponseDTO> getCustomerReviews(Integer customerId);
    // Delete a review by its ID
    void removeReview(Integer reviewId);
}
