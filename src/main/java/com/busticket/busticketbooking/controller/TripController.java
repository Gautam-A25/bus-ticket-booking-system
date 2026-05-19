package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.TripDTO.TripRequestDto;
import com.busticket.busticketbooking.dto.TripDTO.TripResponseDto;
import com.busticket.busticketbooking.service.TripService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping
    public List<TripResponseDto> getAllTrips() {

        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public TripResponseDto getTripById(@PathVariable Integer id) {

        return tripService.getTripById(id);
    }

    @PostMapping
    public TripResponseDto addTrip(
            @Valid @RequestBody TripRequestDto tripRequestDto) {

        return tripService.addTrip(tripRequestDto);
    }

    @PutMapping("/{id}")
    public TripResponseDto updateTrip(
            @PathVariable Integer id,
            @Valid @RequestBody TripRequestDto tripRequestDto) {

        return tripService.updateTrip(id, tripRequestDto);
    }

    @PatchMapping("/{id}/close")
    public String closeTrip(@PathVariable Integer id) {

        tripService.closeTrip(id);

        return "Trip closed successfully";
    }

    @GetMapping("/search")
    public List<TripResponseDto> searchTrips(
            @RequestParam String fromCity,
            @RequestParam String toCity) {

        return tripService.searchTrips(fromCity, toCity);
    }

    @GetMapping("/{id}/seats")
    public Integer getAvailableSeats(@PathVariable Integer id) {

        return tripService.getAvailableSeats(id);
    }
}