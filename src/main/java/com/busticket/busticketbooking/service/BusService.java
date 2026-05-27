package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface defining all bus management operations.
 *
 * <p>A {@code Bus} belongs to an {@code AgencyOffice} and is assigned to
 * {@code Trip}s. Deleting a bus cascades through all dependent trips,
 * bookings, payments, and reviews to maintain referential integrity.</p>
 *
 * <p>The service layer contains business logic; the controller delegates
 * to this interface, and the repository layer performs the actual DB operations.</p>
 */
public interface BusService {

    /**
     * Creates and persists a new bus.
     *
     * @param dto contains bus request data received from client
     * @return the saved bus response DTO
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the associated agency office is not found
     */
    BusResponseDTO createBus(BusRequestDTO dto);

    /**
     * Retrieves all buses in the system.
     *
     * @return a list of all bus response DTOs
     */
    List<BusResponseDTO> getAllBuses();

    /**
     * Retrieves a single bus by its ID.
     *
     * @param id primary key of the bus
     * @return the bus response DTO
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the bus is not found
     */
    BusResponseDTO getBusById(Integer id);

    /**
     * Retrieves all buses belonging to a specific agency office.
     *
     * @param officeId ID of the agency office
     * @return a list of buses for that office
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the agency office is not found
     */
    List<BusResponseDTO> getBusesByOffice(Integer officeId);

    /**
     * Updates an existing bus's details.
     *
     * @param busId ID of the bus to update
     * @param dto   updated bus details
     * @return the updated bus response DTO
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the bus or new agency office is not found
     */
    BusResponseDTO updateBus(
            Integer busId,
            BusRequestDTO dto
    );

    /** Deletes a bus and cascades through all dependent trips, bookings, payments, and reviews.
     *
     * @return a formatted summary string of the deleted record
     */
    String deleteBus(Integer id);

    /** Returns a paginated slice of all buses. */
    Page<BusResponseDTO> getBusPage(int page, int size);
}