package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Review} database operations.
 *
 * <p>Handles queries for trip and customer feedback reviews, along with sequence key generation values.</p>
 */
@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {
    /**
     * Finds all reviews associated with a specific trip.
     *
     * @param tripId ID of the trip
     * @return a list of reviews for the trip
     */
    List<Review> findByTripId(Integer tripId);

    /**
     * Finds all reviews submitted by a specific customer.
     *
     * @param customerId ID of the customer
     * @return a list of reviews submitted by this customer
     */
    List<Review> findByCustomerId(Integer customerId);

    /**
     * Finds the maximum review ID currently present in the database, defaulting to 0.
     *
     * @return the maximum review ID or 0 if none exist
     */
    @Query("SELECT COALESCE(MAX(r.id), 0) FROM Review r")
    Integer findMaxId();
}
