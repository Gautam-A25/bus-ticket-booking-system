package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepo tripRepo;

    @Override
    public List<Trip> getAllTrips() {
        return tripRepo.findAll();
    }

    @Override
    public Trip getTripById(Integer id) {
        return tripRepo.findById(id).orElse(null);
    }

    @Override
    public Trip addTrip(Trip trip) {
        return tripRepo.save(trip);
    }
}