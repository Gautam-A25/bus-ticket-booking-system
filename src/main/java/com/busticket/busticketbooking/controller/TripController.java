package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.TripDTO.TripRequestDTO;
import com.busticket.busticketbooking.dto.TripDTO.TripResponseDTO;
import com.busticket.busticketbooking.dto.TripDTO.SeatAvailabilityDTO;
import com.busticket.busticketbooking.service.TripService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping
    public List<TripResponseDTO> getAllTrips() {

        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public TripResponseDTO getTripById(@PathVariable Integer id) {

        return tripService.getTripById(id);
    }

    @PostMapping
    public TripResponseDTO addTrip(
            @Valid @RequestBody TripRequestDTO tripRequestDto) {

        return tripService.addTrip(tripRequestDto);
    }

    @PutMapping("/{id}")
    public TripResponseDTO updateTrip(
            @PathVariable Integer id,
            @Valid @RequestBody TripRequestDTO tripRequestDto) {

        return tripService.updateTrip(id, tripRequestDto);
    }

    @PatchMapping("/{id}/close")
    public String closeTrip(@PathVariable Integer id) {

        tripService.closeTrip(id);

        return "Trip closed successfully";
    }

    @GetMapping("/search")
    public List<TripResponseDTO> searchTrips(
            @RequestParam String fromCity,
            @RequestParam String toCity,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (date != null) {
            return tripService.searchTrips(fromCity, toCity, date);
        }
        return tripService.searchTrips(fromCity, toCity);
    }

    @GetMapping("/{id}/seats")
    public List<SeatAvailabilityDTO> getSeatAvailability(@PathVariable Integer id) {

        return tripService.getSeatAvailability(id);
    }

    @GetMapping("/{id}/seats/booked")
    public List<Integer> getBookedSeats(@PathVariable Integer id) {

        return tripService.getBookedSeats(id);
    }

    @GetMapping("/{id}/seats/available")
    public List<Integer> getAvailableSeatList(@PathVariable Integer id) {

        return tripService.getAvailableSeatList(id);
    }
}