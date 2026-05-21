package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.ReviewDTO.ReviewRequestDTO;
import com.busticket.busticketbooking.dto.ReviewDTO.ReviewResponseDTO;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.entity.Review;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.repo.ReviewRepo;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.service.ReviewService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.exception.InvalidOperationException;
import com.busticket.busticketbooking.exception.UnauthorizedActionException;
import com.busticket.busticketbooking.mapper.ReviewMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Concrete implementation of ReviewService; handles all review business logic
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepo;
    private final TripRepo tripRepo;
    private final CustomerRepo customerRepo;

    // Constructor injection — Spring injects all three repositories automatically
    public ReviewServiceImpl(ReviewRepo reviewRepo, TripRepo tripRepo, CustomerRepo customerRepo) {
        this.reviewRepo = reviewRepo;
        this.tripRepo = tripRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public ReviewResponseDTO submitReview(Integer tripId, ReviewRequestDTO requestDTO) {
        // Verify the trip exists; throw 404 if not found
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip with ID " + tripId + " not found"));
        // Verify the customer exists; throw 404 if not found
        Customer customer = customerRepo.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID " + requestDTO.getCustomerId() + " not found"));

        // Extra safety check: rating must be between 1 and 5
        if (requestDTO.getRating() < 1 || requestDTO.getRating() > 5) {
            throw new InvalidOperationException("Rating must be between 1 and 5 stars");
        }

        // Block review submission if the trip hasn't departed yet
        if (trip.getDepartureTime() != null && trip.getDepartureTime().isAfter(LocalDateTime.now())) {
            throw new UnauthorizedActionException("Cannot review a trip that has not departed yet.");
        }

        // Build and populate the Review entity
        Review review = new Review();
        review.setTrip(trip);
        review.setCustomer(customer);
        review.setRating(requestDTO.getRating());
        review.setComment(requestDTO.getComment());
        review.setReviewDate(LocalDateTime.now());  // Record current time as review date

        // Save to database and return as response DTO
        Review savedReview = reviewRepo.save(review);
        return ReviewMapper.mapToResponseDTO(savedReview);
    }

    @Override
    public List<ReviewResponseDTO> getTripReviews(Integer tripId) {
        // Fetch all reviews for the trip and convert each to a response DTO
        return reviewRepo.findByTripId(tripId).stream()
                .map(ReviewMapper::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getCustomerReviews(Integer customerId) {
        // Fetch all reviews by the customer and convert each to a response DTO
        return reviewRepo.findByCustomerId(customerId).stream()
                .map(ReviewMapper::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void removeReview(Integer reviewId) {
        // Delete the review by its ID from the database
        reviewRepo.deleteById(reviewId);
    }
}
