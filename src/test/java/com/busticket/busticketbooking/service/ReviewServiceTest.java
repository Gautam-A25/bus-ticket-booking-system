// Total tests: 8
package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.ReviewDTO.ReviewRequestDTO;
import com.busticket.busticketbooking.dto.ReviewDTO.ReviewResponseDTO;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.entity.Review;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.exception.InvalidOperationException;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.exception.UnauthorizedActionException;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.repo.ReviewRepo;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Unit tests for ReviewServiceImpl using Mockito (no real database or Spring context)
@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    // Mock repositories — these simulate DB behaviour without hitting a real database
    @Mock
    private ReviewRepo reviewRepo;

    @Mock
    private TripRepo tripRepo;

    @Mock
    private CustomerRepo customerRepo;

    // Inject the mocks into the actual service implementation being tested
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private ReviewRequestDTO requestDTO;
    private Trip trip;
    private Customer customer;
    private Review review;

    // Set up reusable test data before each test runs
    @BeforeEach
    public void setUp() {
        // A sample review request: customer 2, rating 5, with a comment
        requestDTO = new ReviewRequestDTO();
        requestDTO.setCustomerId(2);
        requestDTO.setRating(5);
        requestDTO.setComment("Amazing journey!");

        // A trip that departed 1 day ago (so reviews can be submitted)
        trip = new Trip();
        trip.setId(11);
        trip.setDepartureTime(LocalDateTime.now().minusDays(1)); // Departed in the past

        customer = new Customer();
        customer.setId(2);

        // A fully populated review entity that represents the saved result
        review = new Review();
        review.setId(50);
        review.setTrip(trip);
        review.setCustomer(customer);
        review.setRating(5);
        review.setComment("Amazing journey!");
        review.setReviewDate(LocalDateTime.now());
    }

    /**
     * 1. testSubmitReview_Success - Verify that a review is successfully submitted for a completed trip.
     */
    @Test
    public void testSubmitReview_Success() {
        when(tripRepo.findById(11)).thenReturn(Optional.of(trip));
        when(customerRepo.findById(2)).thenReturn(Optional.of(customer));
        when(reviewRepo.save(any(Review.class))).thenReturn(review);

        ReviewResponseDTO response = reviewService.submitReview(11, requestDTO);

        assertNotNull(response);
        assertEquals(50, response.getReviewId());
        assertEquals("Amazing journey!", response.getComment());
    }

    /**
     * 2. testSubmitReview_TripNotFound_ThrowsException - Verify missing Trip ID throws ResourceNotFoundException.
     */
    @Test
    public void testSubmitReview_TripNotFound_ThrowsException() {
        when(tripRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> reviewService.submitReview(999, requestDTO));
    }

    /**
     * 3. testSubmitReview_CustomerNotFound_ThrowsException - Verify missing Customer ID throws ResourceNotFoundException.
     */
    @Test
    public void testSubmitReview_CustomerNotFound_ThrowsException() {
        when(tripRepo.findById(11)).thenReturn(Optional.of(trip));
        when(customerRepo.findById(2)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> reviewService.submitReview(11, requestDTO));
    }

    /**
     * 4. testSubmitReview_InvalidRating_ThrowsException - Verify rating outside bounds (1-5) triggers InvalidOperationException.
     */
    @Test
    public void testSubmitReview_InvalidRating_ThrowsException() {
        requestDTO.setRating(6);
        when(tripRepo.findById(11)).thenReturn(Optional.of(trip));
        when(customerRepo.findById(2)).thenReturn(Optional.of(customer));

        assertThrows(InvalidOperationException.class, () -> reviewService.submitReview(11, requestDTO));
    }

    /**
     * 5. testSubmitReview_TripNotDepartedYet_ThrowsException - Verify reviewing pre-departure trips triggers UnauthorizedActionException.
     */
    @Test
    public void testSubmitReview_TripNotDepartedYet_ThrowsException() {
        trip.setDepartureTime(LocalDateTime.now().plusDays(2)); // Departure in the future
        when(tripRepo.findById(11)).thenReturn(Optional.of(trip));
        when(customerRepo.findById(2)).thenReturn(Optional.of(customer));

        assertThrows(UnauthorizedActionException.class, () -> reviewService.submitReview(11, requestDTO));
    }

    /**
     * 6. testGetTripReviews_Success - Verify listing reviews by trip ID.
     */
    @Test
    public void testGetTripReviews_Success() {
        when(reviewRepo.findByTripId(11)).thenReturn(Arrays.asList(review));
        List<ReviewResponseDTO> response = reviewService.getTripReviews(11);
        assertEquals(1, response.size());
    }

    /**
     * 7. testGetCustomerReviews_Success - Verify listing reviews by customer ID.
     */
    @Test
    public void testGetCustomerReviews_Success() {
        when(reviewRepo.findByCustomerId(2)).thenReturn(Arrays.asList(review));
        List<ReviewResponseDTO> response = reviewService.getCustomerReviews(2);
        assertEquals(1, response.size());
    }

    /**
     * 8. testRemoveReview_Success - Verify that removing a review triggers correct deletion.
     */
    @Test
    public void testRemoveReview_Success() {
        doNothing().when(reviewRepo).deleteById(50);
        assertAll(() -> reviewService.removeReview(50));
    }
}
