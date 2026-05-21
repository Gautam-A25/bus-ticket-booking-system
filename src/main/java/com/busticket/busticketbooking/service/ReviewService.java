package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.ReviewDTO.ReviewRequestDTO;
import com.busticket.busticketbooking.dto.ReviewDTO.ReviewResponseDTO;
import org.springframework.data.domain.Page;
import java.util.List;

// Service interface defining all review business operations
public interface ReviewService {
    ReviewResponseDTO submitReview(Integer tripId, ReviewRequestDTO requestDTO);
    List<ReviewResponseDTO> getTripReviews(Integer tripId);
    List<ReviewResponseDTO> getCustomerReviews(Integer customerId);
    void removeReview(Integer reviewId);
    Page<ReviewResponseDTO> getReviewPage(int page, int size);
}
