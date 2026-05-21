package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;

import java.util.List;

/*
 * Service layer contains business logic of application.
 *
 * Controller layer calls Service layer.
 * Service layer communicates with Repository layer.
 *
 * This interface defines all operations
 * related to Driver module.
 */
public interface DriverService {

    /*
     * Creates and saves a new driver.
     *
     * Parameter:
     * dto -> Contains driver request data received from client.
     *
     * Returns:
     * Saved DriverResponseDTO.
     */
    DriverResponseDTO createDriver(DriverRequestDTO dto);

    /*
     * Fetches all drivers from database.
     *
     * Returns:
     * List of all driver response DTOs.
     */
    List<DriverResponseDTO> getAllDrivers();

    /*
     * Fetches a single driver using driver ID.
     *
     * Parameter:
     * id -> Primary key of driver.
     *
     * Returns:
     * DriverResponseDTO containing driver details.
     */
    DriverResponseDTO getDriverById(Integer id);

    /*
     * Fetches all drivers belonging to a specific office.
     *
     * Parameter:
     * officeId -> ID of agency office.
     *
     * Returns:
     * List of drivers for that office.
     */
    List<DriverResponseDTO> getDriversByOffice(Integer officeId);

    /*
     * Updates existing driver details.
     *
     * Parameters:
     * driverId -> ID of driver to update
     * dto      -> Updated request data
     *
     * Returns:
     * Updated DriverResponseDTO
     */
    DriverResponseDTO updateDriver(
            Integer driverId,
            DriverRequestDTO dto
    );

    /*
     * Deletes driver from database using ID.
     *
     * Parameter:
     * id -> Driver ID
     */
    void deleteDriver(Integer id);
}