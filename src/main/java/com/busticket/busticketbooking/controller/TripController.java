package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.TripDTO.TripRequestDTO;
import com.busticket.busticketbooking.dto.TripDTO.TripResponseDTO;
import com.busticket.busticketbooking.service.TripService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
 * RestController is used to create REST APIs.
 * All methods return JSON responses.
 */
@RestController
@RequestMapping("/api/v1/trips")
public class TripController {
    /*
     * Injecting TripService dependency.
     * Service layer contains business logic.
     */
    @Autowired
    private TripService tripService;
      /*
     * GET API to fetch all trips.
     *
     * URL:
     * GET /api/v1/trips
     *
     * Returns:
     * List of all trips.
     */
    @GetMapping
    public List<TripResponseDTO> getAllTrips() {

        return tripService.getAllTrips();
    }
     /*
     * GET API to fetch trip by ID.
     *
     * URL:
     * GET /api/v1/trips/{id}
     *
     * Example:
     * GET /api/v1/trips/1
     *
     * @PathVariable extracts ID from URL.
     */
    @GetMapping("/{id}")
    public TripResponseDTO getTripById(@PathVariable Integer id) {

        return tripService.getTripById(id);
    }

    @PostMapping
    public TripResponseDTO addTrip(
            @Valid @RequestBody TripRequestDTO tripRequestDto) {

        return tripService.addTrip(tripRequestDto);
    }
     /*
     * POST API to create a new trip.
     *
     * URL:
     * POST /api/v1/trips
     *
     * @RequestBody converts JSON request into Java object.
     *
     * @Valid performs validation on DTO fields.
     */
    @PutMapping("/{id}")
    public TripResponseDTO updateTrip(
            @PathVariable Integer id,
            @Valid @RequestBody TripRequestDTO tripRequestDto) {

        return tripService.updateTrip(id, tripRequestDto);
    }
     /*
     * PATCH API to close a trip.
     *
     * URL:
     * PATCH /api/v1/trips/{id}/close
     *
     * Example:
     * PATCH /api/v1/trips/1/close
     *
     * This API can be used when:
     * - trip is completed
     * - booking should stop
     * - available seats become zero
     */
    @PatchMapping("/{id}/close")
    public String closeTrip(@PathVariable Integer id) {

        tripService.closeTrip(id);

        return "Trip closed successfully";
    }
     /*
     * GET API to search trips between cities.
     *
     * URL:
     * GET /api/v1/trips/search
     *
     * Example:
     * GET /api/v1/trips/search?fromCity=Chennai&toCity=Bangalore
     *
     * @RequestParam extracts query parameters from URL.
     */
    @GetMapping("/search")
    public List<TripResponseDTO> searchTrips(
            @RequestParam String fromCity,
            @RequestParam String toCity) {

        return tripService.searchTrips(fromCity, toCity);
    }
     /*
     * GET API to check available seats in a trip.
     *
     * URL:
     * GET /api/v1/trips/{id}/seats
     *
     * Example:
     * GET /api/v1/trips/1/seats
     *
     * Returns:
     * Number of available seats.
     */
    @GetMapping("/{id}/seats")
    public Integer getAvailableSeats(@PathVariable Integer id) {

        return tripService.getAvailableSeats(id);
    }
}