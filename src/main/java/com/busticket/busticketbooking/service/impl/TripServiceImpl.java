package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.TripDTO.TripRequestDto;
import com.busticket.busticketbooking.dto.TripDTO.TripResponseDto;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.entity.Route;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.service.TripService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepo tripRepo;

    @Override
    public List<TripResponseDto> getAllTrips() {

        List<Trip> trips = tripRepo.findAll();

        return trips.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TripResponseDto getTripById(Integer id) {

        Trip trip = tripRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Trip not found with id: " + id));

        return mapToResponseDto(trip);
    }

    @Override
    public TripResponseDto addTrip(TripRequestDto tripRequestDto) {

        Trip trip = mapToEntity(tripRequestDto);

        Trip savedTrip = tripRepo.save(trip);

        return mapToResponseDto(savedTrip);
    }

    @Override
    public TripResponseDto updateTrip(Integer id,
                                      TripRequestDto tripRequestDto) {

        Trip existingTrip = tripRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Trip not found with id: " + id));

        existingTrip.setRoute(createRoute(tripRequestDto.getRouteId()));
        existingTrip.setBus(createBus(tripRequestDto.getBusId()));
        existingTrip.setBoardingAddress(
                createAddress(tripRequestDto.getBoardingAddressId()));

        existingTrip.setDroppingAddress(
                createAddress(tripRequestDto.getDroppingAddressId()));

        existingTrip.setDriver1(
                createDriver(tripRequestDto.getDriver1Id()));

        existingTrip.setDriver2(
                createDriver(tripRequestDto.getDriver2Id()));

        existingTrip.setDepartureTime(
                tripRequestDto.getDepartureTime());

        existingTrip.setArrivalTime(
                tripRequestDto.getArrivalTime());

        existingTrip.setAvailableSeats(
                tripRequestDto.getAvailableSeats());

        existingTrip.setFare(
                tripRequestDto.getFare());

        existingTrip.setTripDate(
                tripRequestDto.getTripDate());

        Trip updatedTrip = tripRepo.save(existingTrip);

        return mapToResponseDto(updatedTrip);
    }

    @Override
    public void closeTrip(Integer id) {

        Trip trip = tripRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Trip not found with id: " + id));

        trip.setAvailableSeats(0);

        tripRepo.save(trip);
    }

    @Override
    public List<TripResponseDto> searchTrips(String fromCity,
                                             String toCity) {

        List<Trip> trips =
                tripRepo.findByRoute_FromCityAndRoute_ToCity(
                        fromCity,
                        toCity);

        return trips.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getAvailableSeats(Integer id) {

        Trip trip = tripRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Trip not found with id: " + id));

        return trip.getAvailableSeats();
    }

    private TripResponseDto mapToResponseDto(Trip trip) {

        TripResponseDto dto = new TripResponseDto();

        dto.setId(trip.getId());

        dto.setRouteId(trip.getRoute().getId());

        dto.setBusId(trip.getBus().getId());

        dto.setBoardingAddressId(
                trip.getBoardingAddress().getId());

        dto.setDroppingAddressId(
                trip.getDroppingAddress().getId());

        dto.setDepartureTime(
                trip.getDepartureTime());

        dto.setArrivalTime(
                trip.getArrivalTime());

        dto.setDriver1Id(
                trip.getDriver1().getId());

        dto.setDriver2Id(
                trip.getDriver2().getId());

        dto.setAvailableSeats(
                trip.getAvailableSeats());

        dto.setFare(
                trip.getFare());

        dto.setTripDate(
                trip.getTripDate());

        return dto;
    }

    private Trip mapToEntity(TripRequestDto dto) {

        Trip trip = new Trip();

        trip.setRoute(createRoute(dto.getRouteId()));

        trip.setBus(createBus(dto.getBusId()));

        trip.setBoardingAddress(
                createAddress(dto.getBoardingAddressId()));

        trip.setDroppingAddress(
                createAddress(dto.getDroppingAddressId()));

        trip.setDriver1(
                createDriver(dto.getDriver1Id()));

        trip.setDriver2(
                createDriver(dto.getDriver2Id()));

        trip.setDepartureTime(dto.getDepartureTime());

        trip.setArrivalTime(dto.getArrivalTime());

        trip.setAvailableSeats(dto.getAvailableSeats());

        trip.setFare(dto.getFare());

        trip.setTripDate(dto.getTripDate());

        return trip;
    }

    private Route createRoute(Integer id) {

        Route route = new Route();

        route.setId(id);

        return route;
    }

    private Bus createBus(Integer id) {

        Bus bus = new Bus();

        bus.setId(id);

        return bus;
    }

    private Address createAddress(Integer id) {

        Address address = new Address();

        address.setId(id);

        return address;
    }

    private Driver createDriver(Integer id) {

        Driver driver = new Driver();

        driver.setId(id);

        return driver;
    }
}