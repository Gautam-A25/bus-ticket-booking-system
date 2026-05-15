package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {
    List<Review> findByTripId(Integer tripId);
    List<Review> findByCustomerId(Integer customerId);
}
