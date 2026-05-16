package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.TripDto;

import java.util.List;

public interface TripService {

    List<TripDto> getAllTrips();

    TripDto getTripById(Integer id);

    TripDto addTrip(TripDto dto);
}