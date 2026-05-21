package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.ReviewDTO.ReviewRequestDTO;
import com.busticket.busticketbooking.dto.ReviewDTO.ReviewResponseDTO;
import java.util.List;

public interface ReviewService {
    ReviewResponseDTO submitReview(Integer tripId, ReviewRequestDTO requestDTO);
    List<ReviewResponseDTO> getTripReviews(Integer tripId);
    List<ReviewResponseDTO> getCustomerReviews(Integer customerId);
    String removeReview(Integer reviewId);
}
