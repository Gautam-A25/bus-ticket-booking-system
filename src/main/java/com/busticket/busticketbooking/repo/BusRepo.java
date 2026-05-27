package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for {@link Bus} database operations.
 *
 * <p>Handles data lookup for physical vehicles assigned to agency branches.</p>
 */
@Repository
public interface BusRepo extends JpaRepository<Bus, Integer> {

    /**
     * Custom finder method to fetch all buses belonging to a particular office ID.
     *
     * <p>Equivalent to SQL: {@code SELECT * FROM buses WHERE office_id = ?}</p>
     *
     * @param officeId ID of the agency office
     * @return a list of buses owned by the office
     */
    List<Bus> findByOffice_Id(Integer officeId);

}