package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping("/trips")
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/trips/{id}")
    public Trip getTripById(@PathVariable Integer id) {
        return tripService.getTripById(id);
    }

    @PostMapping("/trips")
    public Trip addTrip(@RequestBody Trip trip) {
        return tripService.addTrip(trip);
    }
}