package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;
import com.busticket.busticketbooking.service.DriverService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @RestController tells Spring Boot that
 * this class handles REST API requests.
 *
 * It automatically converts Java objects
 * into JSON response.
 */
@RestController

/*
 * Base URL for all Driver APIs.
 *
 * Every API inside this controller
 * will start with:
 *
 * /api/v1/drivers
 */
@RequestMapping("/api/v1/drivers")
public class DriverController {

    /*
     * Service layer object.
     *
     * Controller calls Service layer
     * to perform business logic.
     */
    private final DriverService driverService;

    /*
     * Constructor Injection.
     *
     * Spring automatically injects
     * DriverService dependency here.
     */
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    /*
     * @PostMapping handles HTTP POST requests.
     *
     * Used to create/save new Driver.
     */
    @PostMapping
    public DriverResponseDTO createDriver(

            /*
             * @Valid triggers validation rules
             * defined in DTO class.
             *
             * @RequestBody converts incoming JSON
             * into Java object.
             */
            @Valid @RequestBody DriverRequestDTO dto) {

        /*
         * Calls service layer method
         * to create new driver.
         */
        return driverService.createDriver(dto);
    }

    /*
     * @GetMapping handles HTTP GET requests.
     *
     * Fetches all drivers from database.
     */
    @GetMapping
    public List<DriverResponseDTO> getAllDrivers() {

        /*
         * Calls service layer method
         * to fetch all drivers.
         */
        return driverService.getAllDrivers();
    }

    /*
     * Fetches single driver using Driver ID.
     *
     * Example:
     * GET /api/v1/drivers/1
     */
    @GetMapping("/{id}")
    public DriverResponseDTO getDriverById(

            /*
             * @PathVariable extracts value
             * from URL path.
             */
            @PathVariable Integer id) {

        /*
         * Calls service layer method
         * to fetch driver by ID.
         */
        return driverService.getDriverById(id);
    }

    /*
     * @DeleteMapping handles HTTP DELETE requests.
     *
     * Used to delete driver using ID.
     *
     * Example:
     * DELETE /api/v1/drivers/1
     */
    @DeleteMapping("/{id}")
    public void deleteDriver(

            /*
             * Extract driver ID from URL.
             */
            @PathVariable Integer id) {

        /*
         * Calls service layer method
         * to delete driver.
         */
        driverService.deleteDriver(id);
    }
}