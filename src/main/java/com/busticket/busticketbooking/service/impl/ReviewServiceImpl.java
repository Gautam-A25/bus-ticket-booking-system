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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of {@link ReviewService}.
 *
 * <p>Handles submission, retrieval, and deletion of customer feedback reviews
 * for trips. Enforces rules regarding rating limits and ensures that reviews
 * can only be made on trips that have already departed.</p>
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    /** Repository for performing review database operations. */
    private final ReviewRepo reviewRepo;

    /** Repository for performing trip database operations. */
    private final TripRepo tripRepo;

    /** Repository for performing customer database operations. */
    private final CustomerRepo customerRepo;

    /**
     * Constructs a ReviewServiceImpl with required repository dependencies.
     *
     * @param reviewRepo  repository for review data access
     * @param tripRepo    repository for trip data access
     * @param customerRepo repository for customer data access
     */
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

        // Build and populate the Review entity with sequential manual ID
        Review review = new Review();
        review.setId(reviewRepo.findMaxId() + 1);
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
   /** Deletes a review by ID and returns a formatted summary of the deleted record. */
   public String removeReview(
           Integer reviewId) {

       // Fetch the review; throw 404 if not found
       Review review =
               reviewRepo.findById(reviewId)
                       .orElseThrow(() ->
                               new ResourceNotFoundException(
                                       "Review with ID "
                                               + reviewId
                                               + " not found"));

       // Build a human-readable summary before deleting the record
       String reviewDetails =
               "Review Deleted Successfully : \n" +
                       "ID = " + review.getId() + "\n" +
                       "Customer ID = " +
                       (review.getCustomer() != null
                               ? review.getCustomer().getId()
                               : null) + "\n" +
                       "Trip ID = " +
                       (review.getTrip() != null
                               ? review.getTrip().getId()
                               : null) + "\n" +
                       "Rating = " + review.getRating() + "\n" +
                       "Comment = " + review.getComment() + "\n" +
                       "Review Date = " + review.getReviewDate();

       reviewRepo.delete(review);

       return reviewDetails;
   }

    /** Returns a paginated, newest-first page of all reviews. */
    @Override
    public Page<ReviewResponseDTO> getReviewPage(int page, int size) {
        return reviewRepo.findAll(
                PageRequest.of(page, size, Sort.by("id").descending())
        ).map(ReviewMapper::mapToResponseDTO);
    }

    @Override
    public ReviewResponseDTO getReviewById(Integer id) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review with ID " + id + " not found"));
        return ReviewMapper.mapToResponseDTO(review);
    }
}
