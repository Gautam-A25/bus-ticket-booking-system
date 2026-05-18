package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.ReviewRequestDTO;
import com.busticket.busticketbooking.dto.ReviewResponseDTO;
import java.util.List;

public interface ReviewService {
    ReviewResponseDTO submitReview(Integer tripId, ReviewRequestDTO requestDTO);
    List<ReviewResponseDTO> getTripReviews(Integer tripId);
    List<ReviewResponseDTO> getCustomerReviews(Integer customerId);
    void removeReview(Integer reviewId);
}
