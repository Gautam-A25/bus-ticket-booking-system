package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;

import java.util.List;

/*
 * Service layer contains business logic of application.
 *
 * Controller calls Service layer.
 * Service layer communicates with Repository layer.
 *
 * This interface defines all operations
 * related to Bus module.
 */
public interface BusService {

    /*
     * Creates and saves a new bus.
     *
     * Parameter:
     * dto -> Contains bus request data received from client.
     *
     * Returns:
     * Saved bus response DTO.
     */
    BusResponseDTO createBus(BusRequestDTO dto);

    /*
     * Fetches all buses from database.
     *
     * Returns:
     * List of all bus response DTOs.
     */
    List<BusResponseDTO> getAllBuses();

    /*
     * Fetches a single bus using bus ID.
     *
     * Parameter:
     * id -> Primary key of bus.
     *
     * Returns:
     * BusResponseDTO containing bus details.
     */
    BusResponseDTO getBusById(Integer id);

    /*
     * Fetches all buses belonging to a specific office.
     *
     * Parameter:
     * officeId -> ID of agency office.
     *
     * Returns:
     * List of buses for that office.
     */
    List<BusResponseDTO> getBusesByOffice(Integer officeId);

    /*
     * Updates existing bus details.
     *
     * Parameters:
     * busId -> ID of bus to update
     * dto   -> Updated request data
     *
     * Returns:
     * Updated BusResponseDTO
     */
    BusResponseDTO updateBus(
            Integer busId,
            BusRequestDTO dto
    );

    String deleteBus(Integer id);
}