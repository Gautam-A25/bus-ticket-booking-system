package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.entity.Review;
import java.util.List;

public interface ReviewService {
    Review submitReview(Review review);
    List<Review> getTripReviews(Integer tripId);
    List<Review> getCustomerReviews(Integer customerId);
    void removeReview(Integer reviewId);
}
