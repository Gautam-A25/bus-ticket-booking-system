package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.entity.AgencyOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link AgencyOffice} database operations.
 *
 * <p>Provides custom finder queries to look up offices by parent agency and physical address.</p>
 */
@Repository
public interface AgencyOfficeRepo extends JpaRepository<AgencyOffice, Integer> {
    /**
     * Finds all agency offices belonging to a specific parent agency.
     *
     * @param agencyId the parent agency's ID
     * @return a list of offices associated with the agency
     */
    List<AgencyOffice> findByAgency_Id(Integer agencyId);

    /**
     * Finds all agency offices located at a specific physical address.
     *
     * @param addressId the address record ID
     * @return a list of offices linked to the address ID
     */
    List<AgencyOffice> findByAddressId(Integer addressId);
}