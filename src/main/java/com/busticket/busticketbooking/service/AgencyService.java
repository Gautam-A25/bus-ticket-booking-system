package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.AgencyDTO.AgencyRequestDTO;
import com.busticket.busticketbooking.dto.AgencyDTO.AgencyResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface defining all agency management operations.
 *
 * <p>An agency owns one or more {@code AgencyOffice} records, each of which
 * in turn owns {@code Bus} and {@code Driver} entities. Deleting an agency
 * cascades through all offices, drivers, buses, trips, bookings, payments,
 * and reviews in the correct dependency order.</p>
 */
public interface AgencyService {
    /** Creates and persists a new agency from the given request data. */
    AgencyResponseDTO addAgency(AgencyRequestDTO agencyRequestDTO);

    /** Returns the agency with the given ID; throws 404 if not found. */
    AgencyResponseDTO getAgencyById(Integer id);

    /** Returns all agencies in the system (no pagination). */
    List<AgencyResponseDTO> getAllAgencies();

    /** Returns a paginated, ID-ascending slice of all agencies. */
    Page<AgencyResponseDTO> getAgencyPage(int page, int size);

    /** Updates name, contact person, email, and phone of an existing agency; throws 404 if not found. */
    AgencyResponseDTO updateAgency(Integer id, AgencyRequestDTO agencyRequestDTO);

    /**
     * Deletes an agency and all its dependent data (offices, drivers, buses, trips,
     * bookings, payments, reviews) in the correct order to avoid FK violations.
     *
     * @return a formatted summary string of the deleted record
     */
    String deleteAgency(Integer id);
}