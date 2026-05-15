package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.entity.Trip;

import java.util.List;

public interface TripService {

    List<Trip> getAllTrips();

    Trip getTripById(Integer id);

    Trip addTrip(Trip trip);
}