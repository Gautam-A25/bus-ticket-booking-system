package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Review;
import com.busticket.busticketbooking.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/trips/{tripId}/reviews")
    public ResponseEntity<Review> submitReview(@PathVariable Integer tripId, @RequestBody Review review) {
        // You might want to set the tripId in the review object here if needed
        return ResponseEntity.ok(reviewService.submitReview(review));
    }

    @GetMapping("/trips/{tripId}/reviews")
    public ResponseEntity<List<Review>> getTripReviews(@PathVariable Integer tripId) {
        return ResponseEntity.ok(reviewService.getTripReviews(tripId));
    }

    @GetMapping("/customers/{customerId}/reviews")
    public ResponseEntity<List<Review>> getCustomerReviews(@PathVariable Integer customerId) {
        return ResponseEntity.ok(reviewService.getCustomerReviews(customerId));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> removeReview(@PathVariable Integer reviewId) {
        reviewService.removeReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
