package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;
import com.busticket.busticketbooking.service.BusService;

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
 * Base URL for all Bus APIs.
 *
 * Every API inside this controller
 * will start with:
 *
 * /api/v1/buses
 */
@RequestMapping("/api/v1/buses")
public class BusController {

    /*
     * Service layer object.
     *
     * Controller calls Service layer
     * to perform business logic.
     */
    private final BusService busService;

    /*
     * Constructor Injection.
     *
     * Spring automatically injects
     * BusService dependency here.
     */
    public BusController(BusService busService) {
        this.busService = busService;
    }

    /*
     * @PostMapping handles HTTP POST requests.
     *
     * Used to create/save new Bus.
     */
    @PostMapping
    public BusResponseDTO createBus(

            /*
             * @Valid triggers validation rules
             * defined in DTO class.
             *
             * @RequestBody converts incoming JSON
             * into Java object.
             */
            @Valid @RequestBody BusRequestDTO dto) {

        /*
         * Calls service layer method
         * to create new bus.
         */
        return busService.createBus(dto);
    }

    /*
     * @GetMapping handles HTTP GET requests.
     *
     * Fetches all buses from database.
     */
    @GetMapping
    public List<BusResponseDTO> getAllBuses() {

        /*
         * Calls service layer method
         * to fetch all buses.
         */
        return busService.getAllBuses();
    }

    /*
     * Fetches single bus using Bus ID.
     *
     * Example:
     * GET /api/v1/buses/1
     */
    @GetMapping("/{id}")
    public BusResponseDTO getBusById(

            /*
             * @PathVariable extracts value
             * from URL path.
             */
            @PathVariable Integer id) {

        /*
         * Calls service layer method
         * to fetch bus by ID.
         */
        return busService.getBusById(id);
    }

    /*
     * Fetches all buses belonging to specific office.
     *
     * Example:
     * GET /api/v1/buses/office/2
     */
    @GetMapping("/office/{officeId}")
    public List<BusResponseDTO> getBusesByOffice(

            /*
             * Extract officeId from URL.
             */
            @PathVariable Integer officeId) {

        /*
         * Calls service layer method
         * to fetch buses by office ID.
         */
        return busService.getBusesByOffice(officeId);
    }

    /*
     * @PutMapping handles HTTP PUT requests.
     *
     * Used to update existing bus details.
     *
     * Example:
     * PUT /api/v1/buses/1
     */
    @PutMapping("/{id}")
    public BusResponseDTO updateBus(

            /*
             * Extract bus ID from URL.
             */
            @PathVariable Integer id,

            /*
             * Request body contains updated bus data.
             */
            @Valid @RequestBody BusRequestDTO dto) {

        /*
         * Calls service layer method
         * to update bus details.
         */
        return busService.updateBus(id, dto);
    }

    /*
     * @DeleteMapping handles HTTP DELETE requests.
     *
     * Used to delete bus using ID.
     *
     * Example:
     * DELETE /api/v1/buses/1
     */
    @DeleteMapping("/{id}")
    public void deleteBus(

            /*
             * Extract bus ID from URL.
             */
            @PathVariable Integer id) {

        /*
         * Calls service layer method
         * to delete bus.
         */
        busService.deleteBus(id);
    }
}