package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Payment} database operations.
 *
 * <p>Provides methods to retrieve customer payment histories and locate payments by booking.</p>
 */
@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    /**
     * Finds all payments made by a specific customer.
     *
     * @param customerId ID of the customer
     * @return a list of payments made by this customer
     */
    List<Payment> findByCustomerId(Integer customerId);

    /**
     * Finds the payment record associated with a specific booking.
     *
     * @param bookingId ID of the booking
     * @return an {@link Optional} containing the payment if found, or empty otherwise
     */
    Optional<Payment> findByBookingId(Integer bookingId);
}
