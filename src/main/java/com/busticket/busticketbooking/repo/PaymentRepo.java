package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    List<Payment> findByCustomerId(Integer customerId);
    Optional<Payment> findByBookingId(Integer bookingId);
}
