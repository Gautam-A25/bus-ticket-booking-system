package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.ReviewRequestDTO;
import com.busticket.busticketbooking.dto.ReviewResponseDTO;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.entity.Review;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.repo.ReviewRepo;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.service.ReviewService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepo;
    private final TripRepo tripRepo;
    private final CustomerRepo customerRepo;

    public ReviewServiceImpl(ReviewRepo reviewRepo, TripRepo tripRepo, CustomerRepo customerRepo) {
        this.reviewRepo = reviewRepo;
        this.tripRepo = tripRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public ReviewResponseDTO submitReview(Integer tripId, ReviewRequestDTO requestDTO) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));
        Customer customer = customerRepo.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Review review = new Review();
        review.setTrip(trip);
        review.setCustomer(customer);
        review.setRating(requestDTO.getRating());
        review.setComment(requestDTO.getComment());
        review.setReviewDate(LocalDateTime.now());

        Review savedReview = reviewRepo.save(review);
        return mapToResponseDTO(savedReview);
    }

    @Override
    public List<ReviewResponseDTO> getTripReviews(Integer tripId) {
        return reviewRepo.findByTripId(tripId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getCustomerReviews(Integer customerId) {
        return reviewRepo.findByCustomerId(customerId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void removeReview(Integer reviewId) {
        reviewRepo.deleteById(reviewId);
    }

    private ReviewResponseDTO mapToResponseDTO(Review review) {
        return new ReviewResponseDTO(
                review.getId(),
                review.getCustomer().getId(),
                review.getTrip().getId(),
                review.getRating(),
                review.getComment(),
                review.getReviewDate()
        );
    }
}
