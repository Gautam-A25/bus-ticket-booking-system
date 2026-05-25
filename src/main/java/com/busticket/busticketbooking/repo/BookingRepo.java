package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository layer handles database operations for Booking entity
@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {

    // Checks whether a seat is already booked for a specific trip
    boolean existsByTripIdAndSeatNumber(Integer tripId, Integer seatNumber);

    // Fetches all bookings belonging to a specific trip
    List<Booking> findByTripId(Integer tripId);
}