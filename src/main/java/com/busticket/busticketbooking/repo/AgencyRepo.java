package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Agency} database operations.
 *
 * <p>Extends {@link JpaRepository} to inherit default CRUD operations and pagination queries.</p>
 */
@Repository
public interface AgencyRepo extends JpaRepository<Agency, Integer> {
}