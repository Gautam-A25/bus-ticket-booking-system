package com.busticket.busticketbooking.repo;
import jakarta.transaction.Transactional;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.busticket.busticketbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository layer handles database operations for Booking entity
@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {
    void deleteByTrip_Id(Integer tripId);

    // Checks whether a seat is already booked for a specific trip
    boolean existsByTripIdAndSeatNumber(Integer tripId, Integer seatNumber);

    // Fetches all bookings belonging to a specific trip
    List<Booking> findByTripId(Integer tripId);
    @Transactional

@Modifying

@Query(
        value = """
                DELETE FROM payments
                WHERE booking_id IN
                (
                    SELECT booking_id
                    FROM bookings
                    WHERE trip_id = :tripId
                )
                """,
        nativeQuery = true)

void deletePaymentsByTripId(

        @Param("tripId")
        Integer tripId);



@Transactional

@Modifying

@Query(
        value = """
                DELETE FROM reviews
                WHERE trip_id = :tripId
                """,
        nativeQuery = true)

void deleteReviewsByTripId(

        @Param("tripId")
        Integer tripId);


@Transactional

@Modifying

@Query(
        value = """
                DELETE FROM bookings
                WHERE trip_id = :tripId
                """,
        nativeQuery = true)

void deleteBookingsByTripId(

        @Param("tripId")
        Integer tripId);  
           
}