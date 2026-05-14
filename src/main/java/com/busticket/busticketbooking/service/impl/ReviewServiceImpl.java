package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.entity.Review;
import com.busticket.busticketbooking.repo.ReviewRepo;
import com.busticket.busticketbooking.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepo;

    public ReviewServiceImpl(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Override
    public Review submitReview(Review review) {
        return reviewRepo.save(review);
    }

    @Override
    public List<Review> getTripReviews(Integer tripId) {
        return reviewRepo.findByTripId(tripId);
    }

    @Override
    public List<Review> getCustomerReviews(Integer customerId) {
        return reviewRepo.findByCustomerId(customerId);
    }

    @Override
    public void removeReview(Integer reviewId) {
        reviewRepo.deleteById(reviewId);
    }
}
