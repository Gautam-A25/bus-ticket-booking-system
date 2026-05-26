package com.busticket.busticketbooking.repo;
import jakarta.transaction.Transactional;
import com.busticket.busticketbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Booking} database operations.
 *
 * <p>Exposes default JpaRepository actions, custom query helpers, and native queries
 * to perform cascade-deletions on bookings, payments, and reviews when a trip is removed.</p>
 */
@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {
    /**
     * Deletes all bookings associated with a specific trip.
     *
     * @param tripId ID of the trip
     */
    void deleteByTrip_Id(Integer tripId);

    /**
     * Checks whether a seat is already booked for a specific trip.
     *
     * @param tripId     the ID of the trip
     * @param seatNumber the seat number being checked
     * @return true if the seat is already reserved for the trip, false otherwise
     */
    boolean existsByTripIdAndSeatNumber(Integer tripId, Integer seatNumber);

    /**
     * Finds all bookings associated with a specific trip.
     *
     * @param tripId the ID of the trip
     * @return a list of bookings for the trip
     */
    List<Booking> findByTripId(Integer tripId);

    /**
     * Native query to cascade-delete all payments associated with bookings on a specific trip.
     *
     * @param tripId the ID of the trip
     */
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

    /**
     * Native query to cascade-delete all reviews submitted for a specific trip.
     *
     * @param tripId the ID of the trip
     */
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

    /**
     * Native query to cascade-delete all bookings on a specific trip.
     *
     * @param tripId the ID of the trip
     */
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