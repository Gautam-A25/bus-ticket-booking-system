package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository for Review entity; extends JpaRepository for standard CRUD operations
@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {
    // Returns all reviews for a specific trip
    List<Review> findByTripId(Integer tripId);
    // Returns all reviews written by a specific customer
    List<Review> findByCustomerId(Integer customerId);

    // Find the maximum review ID currently present in the database, defaulting to 0
    @Query("SELECT COALESCE(MAX(r.id), 0) FROM Review r")
    Integer findMaxId();
}
