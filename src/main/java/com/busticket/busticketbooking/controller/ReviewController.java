package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.ReviewDTO.ReviewRequestDTO;
import com.busticket.busticketbooking.dto.ReviewDTO.ReviewResponseDTO;
import com.busticket.busticketbooking.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller for all review-related API endpoints
@RestController
@RequestMapping("/api/v1")
public class ReviewController {

    // Service layer injected via constructor
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // POST /api/v1/trips/{tripId}/reviews — submit a new review for a specific trip
    @PostMapping("/trips/{tripId}/reviews")
    public ResponseEntity<ReviewResponseDTO> submitReview(@PathVariable Integer tripId, @Valid @RequestBody ReviewRequestDTO requestDTO) {
        return ResponseEntity.ok(reviewService.submitReview(tripId, requestDTO));
    }

    // GET /api/v1/trips/{tripId}/reviews — fetch all reviews for a given trip
    @GetMapping("/trips/{tripId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getTripReviews(@PathVariable Integer tripId) {
        return ResponseEntity.ok(reviewService.getTripReviews(tripId));
    }

    // GET /api/v1/customers/{customerId}/reviews — fetch all reviews written by a specific customer
    @GetMapping("/customers/{customerId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getCustomerReviews(@PathVariable Integer customerId) {
        return ResponseEntity.ok(reviewService.getCustomerReviews(customerId));
    }

    // DELETE /api/v1/reviews/{reviewId} — permanently delete a review by its ID; returns 204 No Content
    @DeleteMapping("/reviews/{reviewId}")
    public String removeReview(
            @PathVariable Integer reviewId) {

        return reviewService.removeReview(reviewId);
    }
}
