package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Customer} database operations.
 *
 * <p>Provides validation helpers and address lookup tools for user client records.</p>
 */
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    /**
     * Checks whether a customer email already exists in the database.
     *
     * @param email the email to check
     * @return true if the email is already in use, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Finds all customers associated with a specific address ID.
     *
     * @param addressId ID of the address
     * @return a list of customers at this address
     */
    List<Customer> findByAddressId(Integer addressId);
}