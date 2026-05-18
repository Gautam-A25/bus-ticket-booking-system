package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.ReviewRequestDTO;
import com.busticket.busticketbooking.dto.ReviewResponseDTO;
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
    public ResponseEntity<ReviewResponseDTO> submitReview(@PathVariable Integer tripId, @RequestBody ReviewRequestDTO requestDTO) {
        return ResponseEntity.ok(reviewService.submitReview(tripId, requestDTO));
    }

    @GetMapping("/trips/{tripId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getTripReviews(@PathVariable Integer tripId) {
        return ResponseEntity.ok(reviewService.getTripReviews(tripId));
    }

    @GetMapping("/customers/{customerId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getCustomerReviews(@PathVariable Integer customerId) {
        return ResponseEntity.ok(reviewService.getCustomerReviews(customerId));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> removeReview(@PathVariable Integer reviewId) {
        reviewService.removeReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
