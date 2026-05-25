package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository layer handles database operations for Customer entity
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    // Checks whether customer email already exists in database
    boolean existsByEmail(String email);

    List<Customer> findByAddressId(Integer addressId);
}