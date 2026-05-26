package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Address} database operations.
 *
 * <p>Extends {@link JpaRepository} to inherit default CRUD operations and pagination queries.</p>
 */
@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {
}