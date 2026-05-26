package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeRequestDTO;
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface defining all agency office management operations.
 *
 * <p>An {@code AgencyOffice} is a physical branch of an {@link com.busticket.busticketbooking.entity.Agency}.
 * Each office has an optional address, a contact person, and owns a collection
 * of {@code Bus} and {@code Driver} records. Deleting an office cascades through
 * drivers, buses, trips, bookings, payments, and reviews.</p>
 */
public interface AgencyOfficeService {

    /**
     * Creates a new office under the specified agency.
     *
     * @param agencyId              ID of the parent agency; overrides {@code requestDTO.agencyId} if provided
     * @param agencyOfficeRequestDTO office details including address and contact info
     */
    AgencyOfficeResponseDTO addAgencyOffice(Integer agencyId, AgencyOfficeRequestDTO agencyOfficeRequestDTO);

    /** Returns the office with the given ID; throws 404 if not found. */
    AgencyOfficeResponseDTO getAgencyOfficeById(Integer id);

    /** Returns all offices belonging to the specified agency; throws 404 if the agency doesn't exist. */
    List<AgencyOfficeResponseDTO> getAgencyOfficesByAgencyId(Integer agencyId);

    /** Returns a paginated, ID-ascending slice of all offices. */
    Page<AgencyOfficeResponseDTO> getAgencyOfficePage(int page, int size);

    /** Updates all fields of an existing office; throws 404 if not found. */
    AgencyOfficeResponseDTO updateAgencyOffice(Integer id, AgencyOfficeRequestDTO agencyOfficeRequestDTO);

    /**
     * Deletes an office and cascades through all dependent drivers, buses, trips,
     * bookings, payments, and reviews.
     *
     * @return a formatted summary string of the deleted record
     */
    String deleteAgencyOffice(Integer id);
}