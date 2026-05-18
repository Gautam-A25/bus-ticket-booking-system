package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.TripDto;
import com.busticket.busticketbooking.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping
    public List<TripDto> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public TripDto getTripById(@PathVariable Integer id) {
        return tripService.getTripById(id);
    }

    @PostMapping
    public TripDto addTrip(@RequestBody TripDto dto) {
        return tripService.addTrip(dto);
    }
}