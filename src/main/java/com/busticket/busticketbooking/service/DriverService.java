package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/*
 * Driver Service Interface
 */
public interface DriverService {

    /*
     * Create Driver
     */
    DriverResponseDTO createDriver(DriverRequestDTO dto);

    /*
     * Get all Drivers
     */
    List<DriverResponseDTO> getAllDrivers();

    /*
     * Get paginated Drivers
     */
    Page<DriverResponseDTO> getDriverPage(
            int page,
            int size
    );

    /*
     * Get Driver by ID
     */
    DriverResponseDTO getDriverById(Integer id);

    /*
     * Get Drivers by Office
     */
    List<DriverResponseDTO> getDriversByOffice(Integer officeId);

    /*
     * Update Driver
     */
    DriverResponseDTO updateDriver(
            Integer driverId,
            DriverRequestDTO dto
    );

    /*
     * Delete Driver
     */
    String deleteDriver(Integer id);
}