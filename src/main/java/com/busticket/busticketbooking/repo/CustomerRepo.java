package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);

    List<Customer> findByAddressId(Integer addressId);
}