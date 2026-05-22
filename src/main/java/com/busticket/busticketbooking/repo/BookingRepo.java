package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {
    boolean existsByTripIdAndSeatNumber(Integer tripId, Integer seatNumber);
    List<Booking> findByTripId(Integer tripId);
}