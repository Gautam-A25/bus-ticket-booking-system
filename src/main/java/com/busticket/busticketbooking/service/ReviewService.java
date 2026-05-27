package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.ReviewDTO.ReviewRequestDTO;
import com.busticket.busticketbooking.dto.ReviewDTO.ReviewResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface defining all review business operations.
 *
 * <p>A {@code Review} allows customers to provide feedback (1-5 star ratings and comments)
 * on a {@code Trip} they have completed. Reviews are safety-validated to ensure that only departed
 * trips can be reviewed, and ratings must be within the valid range.</p>
 */
public interface ReviewService {
    /**
     * Submits a new review for a trip.
     *
     * @param tripId     ID of the trip being reviewed
     * @param requestDTO review details including rating, comment, and customer ID
     * @return a {@link ReviewResponseDTO} representing the submitted review
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the trip or customer is not found
     * @throws com.busticket.busticketbooking.exception.InvalidOperationException if the rating is not between 1 and 5
     * @throws com.busticket.busticketbooking.exception.UnauthorizedActionException if trying to review a trip that has not departed yet
     */
    ReviewResponseDTO submitReview(Integer tripId, ReviewRequestDTO requestDTO);

    /**
     * Retrieves all reviews submitted for a given trip.
     *
     * @param tripId ID of the trip
     * @return a list of review response DTOs for the trip
     */
    List<ReviewResponseDTO> getTripReviews(Integer tripId);

    /**
     * Retrieves all reviews written by a specific customer.
     *
     * @param customerId ID of the customer
     * @return a list of review response DTOs written by the customer
     */
    List<ReviewResponseDTO> getCustomerReviews(Integer customerId);

    /**
     * Permanently deletes a review by its ID.
     *
     * @param reviewId ID of the review to delete
     * @return a formatted deletion summary message
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the review is not found
     */
    String removeReview(Integer reviewId);

    /**
     * Retrieves a paginated, ID-descending (newest first) slice of all reviews.
     *
     * @param page zero-based page index
     * @param size maximum number of records per page
     * @return a {@link Page} of review response DTOs
     */
    Page<ReviewResponseDTO> getReviewPage(int page, int size);

    ReviewResponseDTO getReviewById(Integer id);
}