package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.TripDto;
import com.busticket.busticketbooking.entity.*;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.service.TripService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepo tripRepo;

    @Override
    public List<TripDto> getAllTrips() {

        return tripRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public TripDto getTripById(Integer id) {

        Trip trip = tripRepo.findById(id).orElse(null);

        if (trip == null) {
            return null;
        }

        return mapToDto(trip);
    }

    @Override
    public TripDto addTrip(TripDto dto) {

        Trip savedTrip = tripRepo.save(mapToEntity(dto));

        return mapToDto(savedTrip);
    }

    private TripDto mapToDto(Trip trip) {

        TripDto dto = new TripDto();

        dto.setId(trip.getId());
        dto.setRouteId(trip.getRoute().getId());
        dto.setBusId(trip.getBus().getId());
        dto.setBoardingAddressId(trip.getBoardingAddress().getId());
        dto.setDroppingAddressId(trip.getDroppingAddress().getId());
        dto.setDepartureTime(trip.getDepartureTime());
        dto.setArrivalTime(trip.getArrivalTime());
        dto.setDriver1Id(trip.getDriver1().getId());
        dto.setDriver2Id(trip.getDriver2().getId());
        dto.setAvailableSeats(trip.getAvailableSeats());
        dto.setFare(trip.getFare());
        dto.setTripDate(trip.getTripDate());

        return dto;
    }

    private Trip mapToEntity(TripDto dto) {

        Trip trip = new Trip();

        trip.setId(dto.getId());

        Route route = new Route();
        route.setId(dto.getRouteId());
        trip.setRoute(route);

        Bus bus = new Bus();
        bus.setId(dto.getBusId());
        trip.setBus(bus);

        Address boarding = new Address();
        boarding.setId(dto.getBoardingAddressId());
        trip.setBoardingAddress(boarding);

        Address dropping = new Address();
        dropping.setId(dto.getDroppingAddressId());
        trip.setDroppingAddress(dropping);

        Driver driver1 = new Driver();
        driver1.setId(dto.getDriver1Id());
        trip.setDriver1(driver1);

        Driver driver2 = new Driver();
        driver2.setId(dto.getDriver2Id());
        trip.setDriver2(driver2);

        trip.setDepartureTime(dto.getDepartureTime());
        trip.setArrivalTime(dto.getArrivalTime());
        trip.setAvailableSeats(dto.getAvailableSeats());
        trip.setFare(dto.getFare());
        trip.setTripDate(dto.getTripDate());

        return trip;
    }
}