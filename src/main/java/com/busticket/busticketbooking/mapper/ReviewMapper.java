package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.ReviewDTO.ReviewRequestDTO;
import com.busticket.busticketbooking.dto.ReviewDTO.ReviewResponseDTO;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.entity.Review;
import com.busticket.busticketbooking.entity.Trip;

import java.time.LocalDateTime;

// Utility class to convert between Review entity and its DTOs
public class ReviewMapper {

    // Converts a ReviewRequestDTO into a Review entity ready to save to the database
    public static Review mapToEntity(
            ReviewRequestDTO requestDTO,
            Trip trip,
            Customer customer
    ) {
        Review review = new Review();
        review.setTrip(trip);
        review.setCustomer(customer);
        review.setRating(requestDTO.getRating());
        review.setComment(requestDTO.getComment());
        review.setReviewDate(LocalDateTime.now());  // Set current time as review date
        return review;
    }

    // Converts a Review entity into a ReviewResponseDTO to send back in the API response
    public static ReviewResponseDTO mapToResponseDTO(Review review) {
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
