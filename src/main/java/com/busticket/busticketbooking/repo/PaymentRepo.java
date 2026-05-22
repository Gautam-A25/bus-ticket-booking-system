package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Repository for Payment entity; extends JpaRepository for standard CRUD operations
@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    // Returns all payments made by a specific customer
    List<Payment> findByCustomerId(Integer customerId);
    // Returns the payment linked to a specific booking (at most one)
    Optional<Payment> findByBookingId(Integer bookingId);
}
